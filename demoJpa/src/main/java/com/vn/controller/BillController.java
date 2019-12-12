package com.vn.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.Constants;
import com.vn.jpa.Bill;
import com.vn.jpa.Product_Bill;
import com.vn.jpa.Reject;
import com.vn.model.BillModel;
import com.vn.model.ProductBillModel;
import com.vn.service.BillService;
import com.vn.service.ProductService;
import com.vn.service.Product_BillService;
import com.vn.service.RejectService;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/bill/")
public class BillController {

    @Resource
    private BillService billService;

    @Resource
    private Product_BillService productBillService;

    @Resource
    private ProductService productService;

    @Resource
    private RejectService rejectService;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private String DELETE = "N";

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
//    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String list(Model model, HttpSession session, HttpServletRequest request, Pageable pageable,
                       @RequestParam(value = "from_date", defaultValue = "") String fromDate,
                       @RequestParam(value = "to_date", defaultValue = "") String toDate,
                       @RequestParam(value = "code", defaultValue = "") String code,
                       @RequestParam(value = "status", defaultValue = "0", required = false) Integer status) {
        try {
            DateTime time = new DateTime();
            Date _fromDate = time.withTimeAtStartOfDay().toDate();
            Date _toDate = time.withTime(23, 59, 59, 999).toDate();

            if (Strings.isNullOrEmpty(fromDate) && request.getMethod().equalsIgnoreCase("GET")) {
                fromDate = (String) session.getAttribute("from_date");
                if (Strings.isNullOrEmpty(fromDate)) {
                    fromDate = sdf.format(_fromDate);
                }
            }
            if (Strings.isNullOrEmpty(toDate) && request.getMethod().equalsIgnoreCase("GET")) {
                toDate = (String) session.getAttribute("to_date");
                if (Strings.isNullOrEmpty(toDate)) {
                    toDate = sdf.format(_toDate);
                }
            }
            if (Strings.isNullOrEmpty(code) && request.getMethod().equalsIgnoreCase("GET")) {
                code = (String) session.getAttribute("code");
            }
            if (Strings.isNullOrEmpty(String.valueOf(status)) && request.getMethod().equalsIgnoreCase("GET")) {
                status = (Integer) session.getAttribute("status");
            }
            try {
                _fromDate = sdf.parse(fromDate);
                _toDate = sdf.parse(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.addAttribute("from_date", fromDate);
            model.addAttribute("to_date", toDate);
            model.addAttribute("status", status);
            model.addAttribute("code", code);

            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
            Page<Bill> page = billService.findAllBill(_fromDate, _toDate, status, code, DELETE, _pageable);
            model.addAttribute("page", page);
            session.setAttribute("from_date", sdf.format(_fromDate));
            session.setAttribute("to_date", sdf.format(_toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/bill/list";
    }
    
    @RequestMapping(value = "listCancel.html", method = {RequestMethod.GET, RequestMethod.POST})
//  @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
  public String listCancel(Model model, HttpSession session, HttpServletRequest request, Pageable pageable,
                     @RequestParam(value = "from_date", defaultValue = "") String fromDate,
                     @RequestParam(value = "to_date", defaultValue = "") String toDate,
                     @RequestParam(value = "code", defaultValue = "") String code
                   ) {
      try {
          DateTime time = new DateTime();
          Date _fromDate = time.withTimeAtStartOfDay().toDate();
          Date _toDate = time.withTime(23, 59, 59, 999).toDate();

          if (Strings.isNullOrEmpty(fromDate) && request.getMethod().equalsIgnoreCase("GET")) {
              fromDate = (String) session.getAttribute("from_date");
              if (Strings.isNullOrEmpty(fromDate)) {
                  fromDate = sdf.format(_fromDate);
              }
          }
          if (Strings.isNullOrEmpty(toDate) && request.getMethod().equalsIgnoreCase("GET")) {
              toDate = (String) session.getAttribute("to_date");
              if (Strings.isNullOrEmpty(toDate)) {
                  toDate = sdf.format(_toDate);
              }
          }
          if (Strings.isNullOrEmpty(code) && request.getMethod().equalsIgnoreCase("GET")) {
              code = (String) session.getAttribute("code");
          }
          
          try {
              _fromDate = sdf.parse(fromDate);
              _toDate = sdf.parse(toDate);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          model.addAttribute("from_date", fromDate);
          model.addAttribute("to_date", toDate);
          model.addAttribute("code", code);

          Sort sort = new Sort(Sort.Direction.DESC, "id");
          Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
          Page<Reject> page = rejectService.pageByDateAndCode(_fromDate, _toDate, code, _pageable);
          model.addAttribute("page", page);
          session.setAttribute("from_date", sdf.format(_fromDate));
          session.setAttribute("to_date", sdf.format(_toDate));
      } catch (Exception e) {
          e.printStackTrace();
      }
      return "admin/bill/listCancel";
  }

    @RequestMapping(value = "accept/{id}/payment.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String acceptPayment(@PathVariable("id") Long id, HttpSession session) {
        try {
            Bill bill = billService.findOne(id);
            if (bill != null) {
                bill.setStatus(1);
                bill.setCreateBy((String) session.getAttribute("account"));
                billService.update(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/bill/list.html";
    }

    @RequestMapping(value = "getOne/list.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public @ResponseBody
    String getOne(@RequestParam("id") Long id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BillModel model = new BillModel();
        try {
            Bill bill = billService.findOne(id);
            model.setCode(bill.getCode());
            model.setName(bill.getName());
            model.setMobile(bill.getMobile());
            model.setAddress(bill.getAddress());
            model.setTypeStatus(bill.getTypeStatus());
            model.setEmail(bill.getEmail());
            model.setId(bill.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(model);
    }

    @RequestMapping(value = "status/update.html", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String update(@RequestParam("id") Long id, @RequestParam("type") Integer statusType) {
        try {
            Bill bill = billService.findOne(id);
            if (bill != null) {
                bill.setTypeStatus(statusType);
                if (statusType == 0) {
                    bill.setStatus(1);
                }
                billService.update(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/bill/list.html";
    }

    @RequestMapping(value = "billDetail", method = RequestMethod.GET)
    public @ResponseBody
    String findProductBillById(@RequestParam("id") Long idBill) {
        List<Product_Bill> lsproductBill = new ArrayList<Product_Bill>();
        Gson gson = new Gson();
        List<ProductBillModel> lsBillModel = new ArrayList<>();
        try {
            lsproductBill = productBillService.findByBill_Id(idBill);
            for (Product_Bill each : lsproductBill) {
                ProductBillModel billModel = new ProductBillModel();
                billModel.setAddressBill(each.getBill().getAddress());
                billModel.setCodeBill(each.getBill().getCode());
                billModel.setCreatedBill(each.getBill().getCreateDate());
                billModel.setEmailBill(each.getBill().getEmail());
                billModel.setId(each.getId());
                billModel.setMainImg(each.getProduct().getMainImg());
                billModel.setMobileBill(each.getBill().getMobile());
                billModel.setNameBill(each.getBill().getName());
                billModel.setPaymentBill(each.getBill().getPayment());
                billModel.setPriceProduct(each.getProduct().getPrice());
                billModel.setPriceSaleProduct(each.getProduct().getPriceSale());
                billModel.setStatusBill(each.getBill().getStatus());
                billModel.setNameProduct(each.getProduct().getName());
                billModel.setQuantity(each.getQuantity());
                lsBillModel.add(billModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(lsBillModel);
    }
}
