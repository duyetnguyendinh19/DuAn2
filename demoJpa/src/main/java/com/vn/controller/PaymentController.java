package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.jpa.Bill;
import com.vn.model.BillModel;
import com.vn.service.BillService;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public String paymentLive(@RequestBody(required = false) BillModel billModel, HttpSession session, HttpServletRequest request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> responeMap = new HashMap<>();
        try {
            Bill bill = new Bill();

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
