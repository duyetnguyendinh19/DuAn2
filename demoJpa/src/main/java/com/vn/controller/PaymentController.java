package com.vn.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.VnpayConfig;
import com.vn.jpa.*;
import com.vn.model.BillModel;
import com.vn.model.Cart;
import com.vn.model.PaymentUrlModel;
import com.vn.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
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

    @RequestMapping(value = "online/list.html", method = RequestMethod.GET)
    public String paymentOnline(HttpSession session, Model model) {
        AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
        if (authUser != null) {
            model.addAttribute("name", authUser.getFullName());
            model.addAttribute("email", authUser.getEmail());
            Infomation infomation = infomationService.findByAuthUserId(authUser.getId());
            if (infomation != null) {
                model.addAttribute("mobile", infomation.getPhone());
                model.addAttribute("address", infomation.getAddress());
            }
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
//                            HashMap<Long, Cart> map = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
//                            for (Map.Entry<Long, Cart> each : map.entrySet()) {
//                                Product_Bill productBill = new Product_Bill();
//                                Product product = new Product();
//                                product.setId(each.getValue().getProduct().getId());
//                                productBill.setProduct(product);
//                                productBill.setQuantity(each.getValue().getQuantity());
//                                productBill.setIsdelete("N");
//                                productBill.setBill(bill);
//                                productBillService.insert(productBill);
//                            }
                            MimeMessage mimeMessage = mailSender.createMimeMessage();
                            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                            String html = "<div style=\"width: 100%;height: auto;float: left;background-color: #e4e4e4;\">\n" +
                                    "        <div class=\"body\" style=\"margin-top:15px; width: 100%;max-width: 768px;font-family: Nunito Sans, sans-serif!important;background: white;height: auto;margin-bottom: 20px;margin-top: 15px;\n" +
                                    "        margin-left: auto;margin-right:auto;display: table;\">\n" +
                                    "        <div>\n" +
                                    "            <div style=\"text-transform: uppercase;font-size: 13px;font-weight: bold;\n" +
                                    "            color:green;height: 100%;\"></div>\n" +
                                    "            <br>\n" +
                                    "            <div style=\"text-align: center;font-weight:bold;padding-left:40px;padding-right:40px;\n" +
                                    "            padding-bottom:15px;padding-top:15px;font-size: 24px;color: #111194;\">Thư cảm ơn Quý khách đã sử dụng dịch vụ của ÔTôKê</div>\n" +
                                    "            <br>\n" +
                                    "            <div style=\"text-transform: uppercase;border-top: 1px solid green;font-size: 13px;font-weight: bold;\n" +
                                    "            color:green;height: 100%;\"></div>\n" +
                                    "        </div>\n" +
                                    "        <div style=\"float: left;width: 98%;margin-left: 15px;\">\n" +
                                    "            <p style=\"float: left;margin-top: 15px;display: flex\">Xin chào<span class=\"name\"\n" +
                                    "                style=\"float: left;color: #ff9800;font-weight: bold;    margin-left: 4px;\"\n" +
                                    "                >" + bill.getName() + "</span></p>\n" +
                                    "            </div>\n" +
                                    "            <p style=\"margin-left: 15px;margin-top: 15px;margin-right: 15px;line-height: 27px;\">Cảm ơn bạn chọn dịch vụ đặt hàng, mua hàng online của ÔTôKê. Quý khách có góp ý xin vui lòng gửi vào mail <a href=\"mailto:tanbv.dev@gmail.com\">tanbv.dev@gmail.com</a> để ÔTôKê cùng đội ngũ nhân viên khắc phục và phát triển hơn nữa. <br>Xin quý khách vui lòng bỏ thêm một vài phút để thích và xem trước fanpage của ÔTôKê trên facebook tại <a href=\"#\">đây</a> để nhận được những khuyến mãi và các ưu đã của ÔTôKê sớm nhất. Và Quý khách vui lòng vào đánh giá chất lượng sản phẩm theo đường dẫn này <a href=\"#\">feedback.html.</a> Chân thành cảm ơn</p> \n" +
                                    "            <p style=\"margin-left: 15px;margin-top: 15px;margin-right: 15px;line-height: 20px;\">\n" +
                                    "                Xin chúc quý khách hàng sức khỏe, may mắn và thành công.\n" +
                                    "            </p>\n" +
                                    "            <p style=\"margin-left: 15px;margin-top: 15px;margin-right: 15px;line-height: 20px;\"></p>\n" +
                                    "            <p style=\"margin-left: 15px;margin-top: 15px;margin-right: 15px;line-height: 20px;\">\n" +
                                    "                Trân Trọng!\n" +
                                    "            </p>\n" +
                                    "        </div>\n" +
                                    "    </div>";
                            mimeMessage.setContent(html, "text/html; charset=UTF-8"); // content mail
                            mimeMessageHelper.setTo(bill.getEmail());
                            mimeMessageHelper.setSubject("ÔTôKê cam on khach hang");
                            mailSender.send(mimeMessage);

                            session.removeAttribute("myCartItems");
                            session.removeAttribute("myCartTotal");
                            session.removeAttribute("myCartNum");
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
        try {
            AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
            if (authUser == null) {
                responeMap.put("authUser", "Bạn vui lòng đăng nhập trước khi thanh toán");
            }
            if (responeMap.size() == 0) {
                String code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                AuthUser user = new AuthUser();
                user.setId(authUser.getId());
                Bill bill = new Bill();
                bill.setPayment(billModel.getPayment());
                bill.setTotal(billModel.getTotal());
                bill.setIsDelete("N");
                bill.setStatus(2);
                bill.setTypeStatus(Bill.STATUSPAYMENT.ORDER.value());
                bill.setAuthUser(user);
                bill.setAddress(billModel.getAddress());
                bill.setName(billModel.getName());
                bill.setEmail(billModel.getEmail());
                bill.setMobile(billModel.getMobile());
                while (billService.checkExistByCode(code)) {
                    code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                }
                bill.setCode(code);
                billService.insert(bill);

                HashMap<Long, Cart> map = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
                for (Map.Entry<Long, Cart> each : map.entrySet()) {
                    Product product = new Product();
                    Product_Bill productBill = new Product_Bill();
                    product.setId(each.getValue().getProduct().getId());
                    productBill.setProduct(product);
                    productBill.setQuantity(each.getValue().getQuantity());
                    productBill.setIsdelete("N");
                    productBill.setBill(bill);
                    productBillService.insert(productBill);

                    Product pro = productService.findOne(each.getValue().getProduct().getId());
                    if (pro != null) {
                        if (pro.getQuantity() < each.getValue().getQuantity()) {
                            responeMap.put("limit", "Đặt hàng không thành công! Số lượng hàng trong kho không đủ");
                        } else {
                            pro.setQuantity(pro.getQuantity() - each.getValue().getQuantity());
                            productService.update(pro);
                            responeMap.put("success", "Đặt hàng thành công. Xin cảm ơn Quý khách");
                            session.removeAttribute("myCartItems");
                            session.removeAttribute("myCartTotal");
                            session.removeAttribute("myCartNum");
                        }
                    }
                }
            }
        } catch (Exception e) {
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
            Map<String, String> responseMap = new HashMap<>();

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
            user.setId(authUser.getId());
            if (billService.checkExistByCode(code)) {
                code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
            }

            try {
                bill.setCode(code);
                bill.setEmail(model.getEmail());
                bill.setName(model.getName());
                bill.setAddress(model.getAddress());
                bill.setIsDelete("N");
                bill.setStatus(2);
                bill.setTypeStatus(Bill.STATUSPAYMENT.ORDER.value());
                bill.setAuthUser(user);
                bill.setTotal(model.getTotal());
                bill.setMobile(model.getMobile());
                bill.setPayment(Bill.payment.ONLINE.value());
                billService.insert(bill);

                HashMap<Long, Cart> map = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
                for (Map.Entry<Long, Cart> each : map.entrySet()) {
                    Product pro = productService.findOne(each.getValue().getProduct().getId());
                    if (pro != null) {
                        if (pro.getQuantity() < each.getValue().getQuantity()) {
                            responseMap.put("limit", "Đặt hàng không thành công! Số lượng sản phẩm trong kho không đủ.");
                        } else {
                            Product_Bill productBill = new Product_Bill();
//                            Product product = new Product();
//                            product.setId(each.getValue().getProduct().getId());
                            productBill.setProduct(pro);
                            productBill.setQuantity(each.getValue().getQuantity());
                            productBill.setIsdelete("N");
                            productBill.setBill(bill);
                            productBillService.insert(productBill);
                            pro.setQuantity(pro.getQuantity() - each.getValue().getQuantity());
                            productService.update(pro);
                            responseMap.put("urlPayment", paymentUrl);
                        }
                    }
                }
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                String html = "<a href=\"localhost:8284/duan2_war/reject/" + bill.getId() + "/home.html\">" + bill.getId() + "</a>";
                mimeMessage.setContent(html, "text/html; charset=UTF-8"); // content mail
                mimeMessageHelper.setTo(bill.getEmail());
                mimeMessageHelper.setSubject("ÔTôKê đơn đặt hàng");
                mailSender.send(mimeMessage);
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
