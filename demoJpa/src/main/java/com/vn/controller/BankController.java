package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.jpa.BankInfo;
import com.vn.model.BankModel;
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
        List<BankModel> lsmodel = new ArrayList<>();
        try {
            lsInfo = bankInfoService.findAllByType(type);
            for(BankInfo x : lsInfo){
                BankModel model = new BankModel();
                model.setId(x.getId());
                model.setIcon(x.getIcon());
                model.setCode(x.getCode());
                model.setName(x.getName());
                lsmodel.add(model);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return gson.toJson(lsmodel);
    }

    @RequestMapping(value = "findOne.html", method = RequestMethod.GET)
    public @ResponseBody String findOne(@RequestParam("id") Long id){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BankModel model = new BankModel();
        try {
            BankInfo info = bankInfoService.findOne(id);
            model.setInfo(info.getInfo());
        }catch (Exception e){
            e.printStackTrace();
        }
        return gson.toJson(model);
    }

}
