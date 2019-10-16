package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.jpa.BankInfo;
import com.vn.service.BankInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bank/")
public class BankController {

    @Resource
    private BankInfoService bankInfoService;

    @RequestMapping(value = "list.html", method = RequestMethod.GET)
    public @ResponseBody String listBank(@RequestParam("type") Integer type){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<BankInfo> lsInfo = new ArrayList<>() ;
        try {
            lsInfo = bankInfoService.findAllByType(type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return gson.toJson(lsInfo);
    }

}
