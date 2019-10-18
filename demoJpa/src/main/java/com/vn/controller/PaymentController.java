package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.jpa.AuthUser;
import com.vn.jpa.Bill;
import com.vn.jpa.Infomation;
import com.vn.model.BillModel;
import com.vn.service.BillService;
import com.vn.service.InfomationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/payment/")
public class PaymentController {

    @Resource
    private BillService billService;

    @Resource
    private InfomationService infomationService;

    @RequestMapping(value = "online/list.html", method = RequestMethod.GET)
    public String paymentOnline(HttpSession session, Model model) {
        AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
        if (authUser != null) {
            Infomation infomation = infomationService.findByAuthUserId(authUser.getId());
            model.addAttribute("name", authUser.getFullName());
            model.addAttribute("email", authUser.getEmail());
            model.addAttribute("mobile", infomation.getPhone());
            model.addAttribute("address", infomation.getAddress());
        }
        return "home/payment";
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

    @RequestMapping(value = "payingByVNpay", method = RequestMethod.POST)
    public @ResponseBody
    String payingByVNPay(@RequestParam(value = "billId", defaultValue = "") Long id,
                         @RequestParam(value = "bankcode", defaultValue = "") String bankcode) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
