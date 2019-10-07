package com.vn.controller;

import com.google.common.base.Strings;
import com.vn.common.Constants;
import com.vn.jpa.Review;
import com.vn.service.ReviewService;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/review/")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String list(Model model, HttpSession session, HttpServletRequest request, Pageable pageable,
                       @RequestParam(value = "from_date", defaultValue = "") String fromDate,
                       @RequestParam(value = "to_date", defaultValue = "") String toDate,
                       @RequestParam(value = "name", defaultValue = "") String name) {
        try {
            DateTime dateTime = new DateTime();
            Date _fromDate = dateTime.withTimeAtStartOfDay().toDate();
            Date _toDate = dateTime.withTime(23, 59, 59, 999).toDate();

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
                name = (String) session.getAttribute("name");
            }
            try {
                _fromDate = sdf.parse(fromDate);
                _toDate = sdf.parse(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.addAttribute("from_date", fromDate);
            model.addAttribute("to_date", toDate);
            model.addAttribute("name", name);
            if(Strings.isNullOrEmpty(name)){
                name = "";
            }
            Sort sort = new Sort(Direction.DESC, "id");
            Pageable _page = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
            Page<Review> page = reviewService.findALlReview(_fromDate, _toDate, name, _page);
            model.addAttribute("page", page);
            session.setAttribute("from_date", sdf.format(_fromDate));
            session.setAttribute("to_date", sdf.format(_toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/review/list";
    }

    @RequestMapping(value = "delete/{id}/list.html")
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String delete(Model model, @PathVariable("id") Long id){
        try {
            Review review = new Review();
            review.setId(id);
            reviewService.delete(review);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/review/list.html";
    }

}
