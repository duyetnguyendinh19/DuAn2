package com.vn.controller;

import com.google.common.base.Strings;
import com.vn.common.Constants;
import com.vn.jpa.Report;
import com.vn.service.ReportService;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/report/")
public class ReportController {

    @Resource
    private ReportService reportService;

    private SimpleDateFormat sdf_ddMMyyyHHmm = new SimpleDateFormat("dd/MM/yyy HH:mm");

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String listReport(Model model, HttpSession session, Pageable pageable,
                             HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "from_date", defaultValue = "") String fromDate,
                             @RequestParam(value = "to_date", defaultValue = "") String toDate,
                             @RequestParam(value = "name", defaultValue = "") String name) {
        try {
            DateTime dateTime = new DateTime();
            Date _fromDate = dateTime.withTimeAtStartOfDay().toDate();
            Date _toDate = dateTime.withTime(23, 59, 59, 999).toDate();
//            if(Strings.isNullOrEmpty(fromDate) && Strings.isNullOrEmpty(toDate)){
//                fromDate = sdf_ddMMyyyHHmm.format(_fromDate);
//                toDate = sdf_ddMMyyyHHmm.format(_toDate);
//            }
            if (Strings.isNullOrEmpty(fromDate) && request.getMethod().equalsIgnoreCase("GET")) {
                fromDate = (String) session.getAttribute("from_date");
                if (Strings.isNullOrEmpty(fromDate)) {
                    fromDate = sdf_ddMMyyyHHmm.format(_fromDate);
                }
            }
            if (Strings.isNullOrEmpty(toDate) && request.getMethod().equalsIgnoreCase("GET")) {
                toDate = (String) session.getAttribute("to_date");
                if (Strings.isNullOrEmpty(toDate)) {
                    toDate = sdf_ddMMyyyHHmm.format(_toDate);
                }
            }
            if (Strings.isNullOrEmpty(name) && request.getMethod().equalsIgnoreCase("GET")) {
                name = (String) session.getAttribute("name");
            }
            try {
                _fromDate = sdf_ddMMyyyHHmm.parse(fromDate);
                _toDate = sdf_ddMMyyyHHmm.parse(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.addAttribute("from_date", fromDate);
            model.addAttribute("to_date", toDate);
            model.addAttribute("name", name);
            if (Strings.isNullOrEmpty(name)) {
                name = "";
            }
            Sort sort = new Sort(Direction.DESC, "id");
            Pageable _page = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
            Page<Report> page = reportService.findAllReportsReports(_fromDate, _toDate, name, _page);
            model.addAttribute("page", page);
            session.setAttribute("from_date", sdf_ddMMyyyHHmm.format(_fromDate));
            session.setAttribute("to_date", sdf_ddMMyyyHHmm.format(_toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/report/list";
    }
}
