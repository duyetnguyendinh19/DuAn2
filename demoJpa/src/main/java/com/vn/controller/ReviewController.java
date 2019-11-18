package com.vn.controller;

import com.google.common.base.Strings;
import com.vn.common.Constants;
import com.vn.common.ThymeleafUtil;
import com.vn.config.GoogleMailSender;
import com.vn.jpa.Review;
import com.vn.model.ReviewModel;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
            if (Strings.isNullOrEmpty(name)) {
                name = "";
            }
            Sort sort = new Sort(Direction.DESC, "id");
            Pageable _page = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
            Page<Review> page = reviewService.findALlReview(_fromDate, _toDate, name, _page);
            model.addAttribute("page", page);
            session.setAttribute("from_date", sdf.format(_fromDate));
            session.setAttribute("to_date", sdf.format(_toDate));
            session.setAttribute("pageIdx", pageable.getPageNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/review/list";
    }

    @RequestMapping(value = "reply/{id}/list.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String reply(Model model, @PathVariable("id") Long id) {
        try {
            Review review = reviewService.findOne(id);
            ReviewModel reviewModel = new ReviewModel();
            reviewModel.setId(review.getId());
            reviewModel.setStatus(review.getStatus());
            reviewModel.setCreatedDate(review.getCreateDate());
            reviewModel.setDescription(review.getDescription());
            reviewModel.setProduct(review.getProduct());
            reviewModel.setName(review.getName());
            reviewModel.setRate(review.getRate());
            reviewModel.setReply(review.getReply());
            model.addAttribute("review", reviewModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/review/reply";
    }

    @RequestMapping(value = "{id}/reply.html", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String replyPOST(Model model, @ModelAttribute("review") @Valid ReviewModel reviewModel) {
        try {
            if (Strings.isNullOrEmpty(reviewModel.getReply())) {
                model.addAttribute("err", "Trả lời không được để trống");
                model.addAttribute("review", reviewModel);
                return "admin/review/reply";
            }
            Review review = reviewService.findOne(reviewModel.getId());
            if (review != null) {
                review.setReply(reviewModel.getReply());
                reviewService.update(review);
                Map<String, Object> map = new HashMap<>();
                map.put("name", review.getName());
                map.put("text", "Cảm ơn Quý khách đã dành chút thời gian để đánh giá sản phẩm.");
                new Thread(
                        () -> {
                            try {
                                GoogleMailSender mailSender = new GoogleMailSender();
                                final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailThankiuReviewAndReport.html", (HashMap<String, Object>) map);
                                mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", review.getEmail(), "[ÔTôKê] EMail cảm ơn Quý Khách", htmlContent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                ).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/review/list.html";
    }

    @RequestMapping(value = "accept/{id}/list.html")
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String accept(Model model, @PathVariable("id") Long id) {
        try {
            Review review = reviewService.findOne(id);
            review.setStatus(1);
            reviewService.update(review);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/review/list.html";
    }

    @RequestMapping(value = "delete/{id}/list.html")
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String delete(Model model, @PathVariable("id") Long id) {
        try {
            Review review = new Review();
            review.setId(id);
            reviewService.delete(review);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/review/list.html";
    }

}
