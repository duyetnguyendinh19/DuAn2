package com.vn.controller;

import com.google.common.base.Strings;
import com.vn.common.Constants;
import com.vn.jpa.Bill;
import com.vn.service.BillService;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/bill/")
public class BillController {

    @Resource
    private BillService billService;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private String DELETE = "N";

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, HttpSession session, HttpServletRequest request, Pageable pageable,
                       @RequestParam(value = "from_date", defaultValue = "") String fromDate,
                       @RequestParam(value = "to_date", defaultValue = "") String toDate,
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
            if (Strings.isNullOrEmpty(fromDate) && request.getMethod().equalsIgnoreCase("GET")) {
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

            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
            Page<Bill> page = billService.findAllBill(_fromDate, _toDate, status, DELETE, _pageable);
            model.addAttribute("page", page);
            session.setAttribute("from_date", sdf.format(_fromDate));
            session.setAttribute("to_date", sdf.format(_toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/bill/list";
    }
}
