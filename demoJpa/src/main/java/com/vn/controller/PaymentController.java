package com.vn.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.AppUtil;
import com.vn.common.ThymeleafUtil;
import com.vn.common.VnpayConfig;
import com.vn.config.GoogleMailSender;
import com.vn.jpa.*;
import com.vn.model.BillModel;
import com.vn.model.Cart;
import com.vn.service.*;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/payment/")
public class PaymentController {

    @Resource
    private BillService billService;

    @Resource
    private InfomationService infomationService;

    @Resource
    private VnpayTransactionInfoService vnpayTransactionService;

    @Resource
    private ProductService productService;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private Product_BillService productBillService;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private PasswordEncoder passwordEncoder;

    private static int i = 1;

    @ModelAttribute("report")
    public Report report(Model model) {
        model.addAttribute("mapError", new HashedMap<String, String>());
        return new Report();
    }

    @RequestMapping(value = "online/list.html", method = RequestMethod.GET)
    public String paymentOnline(HttpSession session, Model model) {
        AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
        GmailGoogle gmailGoogle = (GmailGoogle) session.getAttribute("userGoogle");
        if (authUser != null) {
            model.addAttribute("name", authUser.getFullName());
            model.addAttribute("email", authUser.getEmail());
            Infomation infomation = infomationService.findByAuthUserId(authUser.getId());
            if (infomation != null) {
                model.addAttribute("mobile", infomation.getPhone());
                model.addAttribute("address", infomation.getAddress());
            }
        }
        if (gmailGoogle != null) {
            model.addAttribute("name", gmailGoogle.getName());
            model.addAttribute("email", gmailGoogle.getEmail());
        }
        return "home/payment";
    }

    @RequestMapping(value = "vnpay-transaction-result", method = RequestMethod.GET)
    public String resultPayment(HttpSession session, HttpServletRequest request) {
        try {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = (String) params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }
            String code = request.getParameter("vnp_TxnRef");
            VnpayTransactionInfo vnpayTransactionInfo = vnpayTransactionService.findByCode(code.trim());
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            String vnp_SecureHashType = request.getParameter("vnp_SecureHashType");

            String signValue = "";
            if ("SHA256".equals(vnp_SecureHashType.trim())) {
                signValue = VnpayConfig.hashAllFields(fields);
            } else {
                signValue = VnpayConfig.hashAllFieldsMD5(fields);
            }
            if (!Strings.isNullOrEmpty(code)) {
                if (vnpayTransactionInfo != null) {
                    Integer vnpayTransStatus = vnpayTransactionInfo.getStatus();
                    if (vnpayTransStatus != VnpayTransactionInfo.VnpayTranStatus.PAID.value()) {
                        if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                            Bill bill = billService.findByCode(code);
                            if (bill != null) {
                                vnpayTransactionInfo.setIdBill(bill.getId());
                                bill.setStatus(1);
                                billService.update(bill);
                            }
                            vnpayTransactionInfo.setVnpBankTranNo(request.getParameter("vnp_BankTranNo"));
                            vnpayTransactionInfo.setVnpTransactionNo(request.getParameter("vnp_TransactionNo"));
                            vnpayTransactionInfo.setVnpPayDate(request.getParameter("vnp_PayDate"));
                            vnpayTransactionInfo.setVnpResponseCode(request.getParameter("vnp_ResponseCode"));
                            vnpayTransactionInfo.setStatus(VnpayTransactionInfo.VnpayTranStatus.PAID.value());
                            vnpayTransactionService.update(vnpayTransactionInfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home/resultPayment";
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public @ResponseBody
    String paymentLive(@RequestBody(required = false) BillModel billModel, HttpSession session, HttpServletRequest
            request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> responeMap = new HashMap<>();
        Map<String, Object> responseMapMail = new HashMap<>();
        try {
            AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
            GmailGoogle gmailGoogle = (GmailGoogle) session.getAttribute("userGoogle");
            if (authUser == null && gmailGoogle == null) {
                responeMap.put("authUser", "Bạn vui lòng đăng nhập trước khi thanh toán");
            } else {
                AuthUser user = new AuthUser();
                Bill bill = new Bill();
                if (authUser != null) {
                    user.setId(authUser.getId());
                    bill.setAuthUser(user);
                } else {
                    AuthUser userAutoEmail = authUserService.findByEmail(billModel.getEmail());
                    if (userAutoEmail == null) {
                        authUser = new AuthUser();
                        List<Role> roles = new ArrayList<>();
                        roles.add(authRoleService.findOne(2l));
                        Date createdDate = new DateTime().toDate();
                        String password = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
                        String salt = "5876695f8e4e1811";
                        String encryptPassword = "";
                        encryptPassword = passwordEncoder.encode(password);
                        authUser.setCreatedDate(createdDate);
                        authUser.setEmail(gmailGoogle.getEmail());
                        authUser.setFirstName(billModel.getName().split(" ")[0]);
                        authUser.setMiddleName(billModel.getName().split(" ")[1]);
                        String lastName = billModel.getName().split(" ")[2];
                        authUser.setLastName(lastName);
                        authUser.setFullName(billModel.getName());
                        authUser.setGender("0");
                        authUser.setIsVerified((byte) 1);
                        authUser.setModifiedDate(null);
                        String userName = AppUtil.convertUnicode(billModel.getName().split(" ")[2].toLowerCase() + billModel.getName().split(" ")[0].substring(0, 1).toLowerCase() + billModel.getName().split(" ")[1].substring(0, 1).toLowerCase());

                        while (authUserService.checkExistByUserName(userName)) {
                            userName = userName + i;
                            i = i++;
                        }
                        authUser.setUserName(userName);
                        authUser.setSalt(salt);
                        authUser.setPassword(encryptPassword);
                        authUser.setStatus((byte) 1);
                        authUser.setUserType((byte) 2);
                        authUser.setAuthRoles(roles);
                        authUserService.create(authUser);
                        bill.setAuthUser(authUser);
                        responseMapMail.put("userName", userName);
                        responseMapMail.put("password", password);
                        responseMapMail.put("name", billModel.getName());
                        new Thread(
                                () -> {
                                    try {
                                        GoogleMailSender mailSender = new GoogleMailSender();
                                        final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailAutoCreateAccount.html", (HashMap<String, Object>) responseMapMail);
                                        mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", billModel.getEmail(), "[ÔTôKê] EMail Tạo Tài Khoản Tự Động", htmlContent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                        ).start();
                    } else {
                        user.setId(userAutoEmail.getId());
                        bill.setAuthUser(user);
                    }
                }
                if (responeMap.size() == 0) {
                	boolean checkPro = true;
                    String code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();

                    bill.setPayment(billModel.getPayment());
                    bill.setTotal(billModel.getTotal());
                    bill.setIsDelete("N");
                    bill.setStatus(2);
                    bill.setTypeStatus(Bill.STATUSPAYMENT.ORDER.value());
//                        bill.setAuthUser(user);
                    bill.setAddress(billModel.getAddress());
                    bill.setName(billModel.getName());
                    bill.setEmail(billModel.getEmail());
                    bill.setMobile(billModel.getMobile());
                    while (billService.checkExistByCode(code)) {
                        code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                    }
                    bill.setCode(code);
                    bill.setMailStatus(Bill.MAILSTATUS.UNPAID.value());
                    HashMap<Long, Cart> map = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
                    for (Map.Entry<Long, Cart> eachValid : map.entrySet()) {
                        Product pro = productService.findOne(eachValid.getValue().getProduct().getId());
                        if (pro.getQuantity() < eachValid.getValue().getQuantity()) {
                            responeMap.put("limit", "Đặt hàng không thành công! Số lượng hàng " + eachValid.getValue().getProduct().getName() 
                            		+ " trong kho không đủ ( chỉ còn " + eachValid.getValue().getProduct().getQuantity() + " sản phẩm )");
                            checkPro = false;
                            break;
                        }
                    }
                    
                    if(checkPro) {
                    	billService.insert(bill);
                        
                        for (Map.Entry<Long, Cart> each : map.entrySet()) {
                            Product pro = productService.findOne(each.getValue().getProduct().getId());
                            if (pro != null) {
                                Product product = new Product();
                                Product_Bill productBill = new Product_Bill();
                                product.setId(each.getValue().getProduct().getId());
                                productBill.setProduct(product);
                                productBill.setQuantity(each.getValue().getQuantity());
                                productBill.setIsdelete("N");
                                productBill.setBill(bill);
                                productBillService.insert(productBill);
                                pro.setQuantity(pro.getQuantity() - each.getValue().getQuantity());
                                productService.update(pro);
                                responeMap.put("success", "Đặt hàng thành công. Xin cảm ơn Quý khách");
                                session.removeAttribute("myCartItems");
                                session.removeAttribute("myCartTotal");
                                session.removeAttribute("myCartNum");
                            }
                        }	
                    }
                    
                    
                    responseMapMail.put("bill", bill);
                    new Thread(
                            () -> {
                                try {
                                    GoogleMailSender mailSender = new GoogleMailSender();
                                    final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailCustomerOrderProduct.html", (HashMap<String, Object>) responseMapMail);
                                    mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", billModel.getEmail(), "[ÔTôKê] EMail đơn đặt hàng Quý Khách", htmlContent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    ).start();
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(responeMap);
    }

    @RequestMapping(value = "payingByVNpay.html", method = RequestMethod.POST)
    public @ResponseBody
    String payingByVNPay(@RequestBody(required = false) BillModel model, HttpServletRequest req, HttpSession
            session) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Map<String, Object> responseMap = new HashMap<>();
            Map<String, Object> responseMapMail = new HashMap<>();

            String code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
            String vnp_OrderInfo = "Thanh toan don hang ma " + code;
            String orderType = "billpayment";
            VnpayTransactionInfo vnpayTrans = new VnpayTransactionInfo();

            Long totalFee = (long) Math.round(model.getTotal());
            totalFee = totalFee * 100;

            String vnp_Version = "2.0.0";
            String vnp_Command = "pay";
            String vnp_IpAddr = VnpayConfig.getIpAddress();

            String vnp_TmnCode = VnpayConfig.vnp_TmnCode;
            String vnp_hashSecret = VnpayConfig.vnp_HashSecret;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(totalFee));
            vnp_Params.put("vnp_CurrCode", "VND");
            if (model.getBankCode() != null && !model.getBankCode().isEmpty()) {
                vnp_Params.put("vnp_BankCode", model.getBankCode());
            }
            vnp_Params.put("vnp_TxnRef", code);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = req.getParameter("language");
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
                vnpayTrans.setVnpLocale(locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
                vnpayTrans.setVnpLocale("vn");
            }
            vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Date dt = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateString = formatter.format(dt);
            String vnp_CreateDate = dateString;
            String vnp_TransDate = vnp_CreateDate;
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            // Build data to hash and querystring
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    // Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(fieldValue);
                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = VnpayConfig.Sha256(VnpayConfig.vnp_HashSecret + hashData.toString());
            queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;

            vnpayTrans.setCode(code);
            vnpayTrans.setStatus(VnpayTransactionInfo.VnpayTranStatus.UNPAID.value());
            vnpayTrans.setVnpAmount(totalFee / 100);
            vnpayTrans.setVnpBankCode(model.getBankCode());
            vnpayTrans.setVnpCreateDate(vnp_CreateDate);
            vnpayTrans.setVnpCurrCode("VND");
            vnpayTrans.setVnpIpAddr(vnp_IpAddr);
            vnpayTrans.setVnpOrderInfo(vnp_OrderInfo);
            vnpayTrans.setVnpOrderType(orderType);
            vnpayTransactionService.insert(vnpayTrans);

            Bill bill = new Bill();
            AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
            AuthUser user = new AuthUser();
            if (authUser != null) {
                user.setId(authUser.getId());
                bill.setAuthUser(user);
            } else {
                GmailGoogle gmailGoogle = (GmailGoogle) session.getAttribute("userGoogle");
                AuthUser userAutoEmail = authUserService.findByEmail(model.getEmail());
                if (userAutoEmail == null) {
                    authUser = new AuthUser();
                    List<Role> roles = new ArrayList<>();
                    roles.add(authRoleService.findOne(2l));
                    Date createdDate = new DateTime().toDate();
                    String password = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
                    String salt = "5876695f8e4e1811";
                    String encryptPassword = "";
                    encryptPassword = passwordEncoder.encode(password);
                    authUser.setCreatedDate(createdDate);
                    authUser.setEmail(gmailGoogle.getEmail());
                    authUser.setFirstName(model.getName().split(" ")[0]);
                    authUser.setMiddleName(model.getName().split(" ")[1]);
                    String lastName = model.getName().split(" ")[2];
                    authUser.setLastName(lastName);
                    authUser.setFullName(model.getName());
                    authUser.setGender("0");
                    authUser.setIsVerified((byte) 1);
                    authUser.setModifiedDate(null);
                    String userName = AppUtil.convertUnicode(model.getName().split(" ")[2].toLowerCase() + model.getName().split(" ")[0].substring(0, 1).toLowerCase() + model.getName().split(" ")[1].substring(0, 1).toLowerCase());
//                    String userName = gmailGoogle.getEmail().split("@")[0];

                    while (authUserService.checkExistByUserName(userName)) {
                        userName = userName + i;
                        i++;
                    }
                    authUser.setUserName(userName);
                    authUser.setSalt(salt);
                    authUser.setPassword(encryptPassword);
                    authUser.setStatus((byte) 1);
                    authUser.setUserType((byte) 2);
                    authUser.setAuthRoles(roles);
                    authUserService.create(authUser);
                    bill.setAuthUser(authUser);
                    responseMapMail.put("userName", userName);
                    responseMapMail.put("password", password);
                    responseMapMail.put("name", model.getName());
                    new Thread(
                            () -> {
                                try {
                                    GoogleMailSender mailSender = new GoogleMailSender();
                                    final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailAutoCreateAccount.html", (HashMap<String, Object>) responseMapMail);
                                    mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", model.getEmail(), "[ÔTôKê] EMail Tạo Tài Khoản Tự Động", htmlContent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    ).start();
                } else {
                    user.setId(userAutoEmail.getId());
                    bill.setAuthUser(user);
                }
            }
            try {
                if (billService.checkExistByCode(code)) {
                    code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                }
                bill.setCode(code);
                bill.setEmail(model.getEmail());
                bill.setName(model.getName());
                bill.setAddress(model.getAddress());
                bill.setIsDelete("N");
                bill.setStatus(2);
                bill.setTypeStatus(Bill.STATUSPAYMENT.ORDER.value());
//                bill.setAuthUser(authUser);
                bill.setTotal(model.getTotal());
                bill.setMobile(model.getMobile());
                bill.setPayment(Bill.payment.ONLINE.value());
                bill.setMailStatus(Bill.MAILSTATUS.UNPAID.value());
                boolean isSuccess = true;
                HashMap<Long, Cart> map = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
                for (Map.Entry<Long, Cart> each : map.entrySet()) {
                    Product pro = productService.findOne(each.getValue().getProduct().getId());
                    if (pro != null) {
                        if (pro.getQuantity() < each.getValue().getQuantity()) {
                            responseMap.put("limit", "Đặt hàng không thành công! Số lượng hàng trong kho không đủ");
                            isSuccess = false;
                            break;
                        } else {
                            billService.insert(bill);
                            Product_Bill productBill = new Product_Bill();
                            productBill.setProduct(pro);
                            productBill.setQuantity(each.getValue().getQuantity());
                            productBill.setIsdelete("N");
                            productBill.setBill(bill);
                            productBillService.insert(productBill);
                            pro.setQuantity(pro.getQuantity() - each.getValue().getQuantity());
                            productService.update(pro);
                            responseMap.put("urlPayment", paymentUrl);
                            session.removeAttribute("myCartItems");
                            session.removeAttribute("myCartTotal");
                            session.removeAttribute("myCartNum");
                            isSuccess = true;
                        }
                    }
                }
                responseMapMail.put("bill", bill);
                if (isSuccess) {
                    new Thread(
                            () -> {
                                try {
                                    GoogleMailSender mailSender = new GoogleMailSender();
                                    final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailCustomerOrderProduct.html", (HashMap<String, Object>) responseMapMail);
                                    mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", model.getEmail(), "[ÔTôKê] EMail đơn đặt hàng Quý Khách", htmlContent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    ).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return gson.toJson(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            return "lỗi" + e;
        }
    }
}
