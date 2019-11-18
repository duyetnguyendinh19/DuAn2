package com.vn.controller;

import com.google.common.base.Strings;
import com.vn.common.Constants;
import com.vn.common.ThymeleafUtil;
import com.vn.config.GoogleMailSender;
import com.vn.jpa.Report;
import com.vn.model.ReportModel;
import com.vn.service.ReportService;
import com.vn.validation.service.ReportFormValidator;

import org.apache.commons.collections4.map.HashedMap;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/report/")
public class ReportController {

    @Resource
    private ReportService reportService;
    
    @Resource
    private ReportFormValidator reportFormValidator;

    private SimpleDateFormat sdf_ddMMyyyHHmm = new SimpleDateFormat("dd/MM/yyy HH:mm");

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String listReport(Model model, HttpSession session, Pageable pageable,
                             HttpServletRequest request, HttpServletResponse response,
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
            request.getSession().setAttribute("pageIndex", pageable.getPageNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/report/list";
    }

    @RequestMapping(value = "{id}/reply.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String reply(Model model, @PathVariable("id") Long id){
       try {
    	   Report report = reportService.findOne(id);
           ReportModel reportModel = new ReportModel();
           reportModel.setId(id);
           reportModel.setEmail(report.getEmail());
           reportModel.setMobile(report.getMobile());
           reportModel.setName(report.getName());
           reportModel.setOpinion(report.getOpinion());
           reportModel.setProblem(report.getProblem());
           reportModel.setReply(report.getRepply());
           model.addAttribute("report", reportModel);
           return "admin/report/update";
		} catch (Exception e) {
			e.printStackTrace();
		}
       return "admin/report/update";
    }

    @RequestMapping(value = "{id}/reply.html", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String reply(Model model, @PathVariable("id") Long id, @ModelAttribute("report") @Valid ReportModel report){
       try {
           if(Strings.isNullOrEmpty(report.getReply())){
               model.addAttribute("err", "Trả lời không được để trống");
               model.addAttribute("report", report);
               return "admin/report/update";
           }
           Report report1 = reportService.findOne(id);
           if(report1 != null){
               report1.setRepply(report.getReply());
               reportService.update(report1);
               Map<String, Object> map = new HashMap<>();
               map.put("name", report1.getName());
               map.put("text", "ÔTôKê cảm ơn Quý khách đã có ý kiến góp ý cho chúng tôi.");
               map.put("problem", "Vấn đề của Quý khách gặp phải là: " + report1.getProblem());
               map.put("opinion", "Ý kiến của Quý khách là: " + report1.getOpinion());
               map.put("rep", "Cảm ơn Quý khách, " + report1.getRepply());
               new Thread(
                       () -> {
                           try {
                               GoogleMailSender mailSender = new GoogleMailSender();
                               final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailThankiuReviewAndReport.html", (HashMap<String, Object>) map);
                               mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", report1.getEmail(), "[ÔTôKê] EMail cảm ơn Quý Khách", htmlContent);
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }
               ).start();
           }
       }catch (Exception e){
           e.printStackTrace();
       }
        return "redirect:/report/list.html";
    }
    
    @RequestMapping(value = "save.html" , method = RequestMethod.POST)
    public String saveReply(@ModelAttribute("report") Report report, BindingResult result,Model model) {
    	Map<String, String> mapError = new HashedMap<String, String>();
    	try {
    		reportFormValidator.validateReportForm(report, result);
        	if(result.hasErrors()) {
        		for(Object obj : result.getAllErrors()) {
        			if(obj instanceof ObjectError) {
        				mapError.put(((ObjectError) obj).getCode(), ((ObjectError) obj).getDefaultMessage());
        			}
        		}
        		model.addAttribute("mapError", mapError);
        		return "home/contact";
        	}else {
        		reportService.insert(report);
        		model.addAttribute("mapError", mapError);
        		model.addAttribute("report", new Report());
        		return "home/thank";
        	}
		} catch (Exception e) {
			e.printStackTrace();
			mapError.put("error", "Lỗi không xác định");
			model.addAttribute("mapError", mapError);
			return "home/contact";
		}
    }
    
    @RequestMapping(value = "saveFooter.html" , method = RequestMethod.POST)
    public String saveReplyFooter(@ModelAttribute("report") Report report, BindingResult result,Model model,HttpServletRequest request) {
    	Map<String, String> mapError = new HashedMap<String, String>();
    	try {
    		reportFormValidator.validateReportFormInFooter(report, result);
        	if(result.hasErrors()) {
        		for(Object obj : result.getAllErrors()) {
        			if(obj instanceof ObjectError) {
        				mapError.put(((ObjectError) obj).getCode(), ((ObjectError) obj).getDefaultMessage());
        			}
        		}
        		model.addAttribute("mapError", mapError);
        		return "home/contact";
        	}else {
        		reportService.insert(report);
        		model.addAttribute("mapError", mapError);
        		model.addAttribute("report", new Report());
        		return "home/thank";
        	}
		} catch (Exception e) {
			e.printStackTrace();
			mapError.put("error", "Lỗi không xác định");
			model.addAttribute("mapError", mapError);
    		return "home/contact";
		}
    }

}
