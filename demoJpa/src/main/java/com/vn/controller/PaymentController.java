package com.vn.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.vn.common.VnpayConfig;
import com.vn.jpa.AuthUser;
import com.vn.jpa.Bill;
import com.vn.jpa.Infomation;
import com.vn.jpa.VnpayTransactionInfo;
import com.vn.model.BankModel;
import com.vn.model.BillModel;
import com.vn.model.PaymentUrlModel;
import com.vn.service.BillService;
import com.vn.service.InfomationService;
import com.vn.service.VnpayTransactionInfoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
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

    @RequestMapping(value = "online/list.html", method = RequestMethod.GET)
    public String paymentOnline(HttpSession session, Model model) {
        AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
        if (authUser != null) {
            model.addAttribute("name", authUser.getFullName());
            model.addAttribute("email", authUser.getEmail());
            Infomation infomation = infomationService.findByAuthUserId(authUser.getId());
            if(infomation != null){
                model.addAttribute("mobile", infomation.getPhone());
                model.addAttribute("address", infomation.getAddress());
            }
        }
        return "home/payment";
    }

    @RequestMapping(value = "vnpay-transaction-result", method = RequestMethod.GET)
    public String resultPayment(HttpSession session){
        session.removeAttribute("myCartItems");
        session.removeAttribute("myCartTotal");
        session.removeAttribute("myCartNum");
        return "home/resultPayment";
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public @ResponseBody
    String paymentLive(@RequestBody(required = false) BillModel billModel, HttpSession session, HttpServletRequest request) {
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
                responeMap.put("success", "Thêm hóa đơn thành công");
                session.removeAttribute("myCartItems");
                session.removeAttribute("myCartTotal");
                session.removeAttribute("myCartNum");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(responeMap);
    }

    @RequestMapping(value = "payingByVNpay.html", method = RequestMethod.POST)
    public @ResponseBody
    String payingByVNPay(@RequestBody(required = false) BillModel model, HttpServletRequest req, HttpSession session) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
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
            vnpayTrans.setVnpAmount(totalFee);
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
            if(billService.checkExistByCode(code)){
                code = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
            }
            try {
                bill.setCode(code);
                bill.setEmail(model.getEmail());
                bill.setName(model.getName());
                bill.setAddress(model.getAddress());
                bill.setIsDelete("N");
                bill.setStatus(2);
                bill.setAuthUser(user);
                bill.setTotal(model.getTotal());
                bill.setMobile(model.getMobile());
                bill.setPayment(Bill.payment.ONLINE.value());
                billService.insert(bill);
            }catch (Exception e){
                e.printStackTrace();
            }
            PaymentUrlModel res = new PaymentUrlModel();
            res.setPaymentUrl(paymentUrl);
            return gson.toJson(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "lỗi" + e;
        }
    }

    @RequestMapping(value = "payingByVNPAYReturn", method = RequestMethod.GET)
    public void payingByVNPAYReturn(HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = (String) params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = VnpayConfig.hashAllFields(fields);

            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                    String bookingCode = request.getParameter("vnp_TxnRef");
                    if (!Strings.isNullOrEmpty(bookingCode)) {
                        VnpayTransactionInfo vnpayTrans = vnpayTransactionService.findByCode(bookingCode.trim());
                        if (vnpayTrans != null) {
                            String vnp_Version = "2.0.0";
                            String vnp_Command = "querydr";
                            String vnp_IpAddr = VnpayConfig.getIpAddress();

                            String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

                            String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");

                            String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
                            String vnp_hashSecret = VnpayConfig.vnp_HashSecret;

                            Map<String, String> vnp_Params = new HashMap<>();
                            vnp_Params.put("vnp_Version", vnp_Version);
                            vnp_Params.put("vnp_Command", vnp_Command);
                            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
                            vnp_Params.put("vnp_TxnRef", bookingCode);
                            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
                            vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
                            vnp_Params.put("vnp_TransDate", vnpayTrans.getVnpCreateDate());

                            Date dt = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                            String dateString = formatter.format(dt);
                            vnp_Params.put("vnp_CreateDate", dateString);

                            InetAddress ip = InetAddress.getLocalHost();
                            vnp_Params.put("vnp_IpAddr", ip.getHostAddress());

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
                            String _vnp_SecureHash = VnpayConfig
                                    .Sha256(VnpayConfig.vnp_HashSecret + hashData.toString());
                            queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + _vnp_SecureHash;
                            String paymentUrl = VnpayConfig.vnp_apiUrl + "?" + queryUrl;
                            // ========================================

                            HttpResponse<String> req = Unirest.get(paymentUrl).asString();
                            if (req.getStatus() == HttpStatus.OK.value()) {
                                String body = req.getBody();
                                Map<String, String> resFields = VnpayConfig.convertBodyToMap(body);
                                List<Object> lsKey = Arrays.asList(resFields.keySet().toArray());
                                for (Object object : lsKey) {
                                    String obj = (String) object;
                                }
                                String res_vnp_SecureHash = resFields.get("vnp_SecureHash");
                                if (resFields.containsKey("vnp_SecureHashType")) {
                                    resFields.remove("vnp_SecureHashType");
                                }
                                if (resFields.containsKey("vnp_SecureHash")) {
                                    resFields.remove("vnp_SecureHash");
                                }
                                String res_signValue = VnpayConfig.hashAllFields(resFields);
                                if (res_signValue.equals(res_vnp_SecureHash)) {
                                    if ("00".equals(resFields.get("vnp_ResponseCode"))) {
                                        if ("00".equals(resFields.get("vnp_TransactionStatus"))) {
                                            if (resFields.get("vnp_Amount").trim()
                                                    .equals(String.valueOf(vnpayTrans.getVnpAmount()))) {
                                                vnpayTrans.setVnpBankTranNo(request.getParameter("vnp_BankTranNo"));
                                                vnpayTrans.setVnpTransactionNo(request.getParameter("vnp_TransactionNo"));
                                                vnpayTrans.setVnpPayDate(request.getParameter("vnp_PayDate"));
                                                vnpayTrans.setStatus(VnpayTransactionInfo.VnpayTranStatus.PAID.value());
                                                vnpayTransactionService.update(vnpayTrans);
                                            }
                                        }
                                    }
                                }

                            }

                        } else {
                            responseMap.put("GD", "GD Khong thanh cong");
                        }
                    } else {
                        responseMap.put("GD", "GD Khong thanh cong");
                    }

                } else {
                    responseMap.put("GD", "GD Khong thanh cong");
                }

            } else {
                responseMap.put("GD", "Chu ky khong hop le");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
