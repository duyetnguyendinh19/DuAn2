package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.jpa.Bill;
import com.vn.jpa.Product;
import com.vn.jpa.Product_Bill;
import com.vn.jpa.Reject;
import com.vn.jpa.Report;
import com.vn.model.RejectModel;
import com.vn.service.BillService;
import com.vn.service.ProductService;
import com.vn.service.Product_BillService;
import com.vn.service.RejectService;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reject/")
public class RejectController {

    @Resource
    private Product_BillService productBillService;

    @Resource
    private BillService billService;

    @Resource
    private RejectService rejectService;

    @Resource
    private ProductService productService;
    
    @ModelAttribute("report")
 	public Report report(Model model) {
 		model.addAttribute("mapError", new HashedMap<String, String>());
 	    return new Report();
 	}

    @RequestMapping(value = "{id}/home.html", method = RequestMethod.GET)
    public String reject(@PathVariable("id") Long id, Model model) {
        List<Product_Bill> lsProductBill = productBillService.findByBill_Id(id);
        Bill bill = billService.findOne(id);
        if (bill != null) {
            model.addAttribute("bill", bill);
        }
        model.addAttribute("reject", lsProductBill);
        return "home/reject";
    }

    @RequestMapping(value = "home.html", method = RequestMethod.POST)
    public @ResponseBody
    String rejectPOST(@RequestBody(required = false) RejectModel rejectModel) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, String> map = new HashMap<>();
        try {
            List<Product_Bill> lsProductBill = productBillService.findByBill_Id(rejectModel.getId());
            Reject reject = new Reject();
            reject.setCode(rejectModel.getCode());
            reject.setAddress(rejectModel.getAddress());
            reject.setEmail(rejectModel.getEmail());
            reject.setMobile(rejectModel.getMobile());
            reject.setName(rejectModel.getName());
            reject.setReason(rejectModel.getReason());
            rejectService.insert(reject);
            // insert reject
            for (Product_Bill each : lsProductBill) {
                Product product = productService.findOne(each.getProduct().getId());
                if(product != null){
                    product.setQuantity(product.getQuantity() + each.getQuantity());
                    productService.update(product);
                    // update số lượng product
                }
            }
            Bill bill = billService.findOne(rejectModel.getId());
            bill.setTypeStatus(Bill.STATUSPAYMENT.UNPAID.value());
            billService.update(bill);
            map.put("success", "Hủy đơn đặt hàng thành công");
            // update trạng thái hủy đơn
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(map);
    }
}
