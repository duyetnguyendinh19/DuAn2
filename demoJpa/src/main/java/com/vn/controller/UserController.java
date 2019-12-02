package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.Constants;
import com.vn.jpa.AuthUser;
import com.vn.jpa.Infomation;
import com.vn.model.AuthUserModel;
import com.vn.model.InfomationModel;
import com.vn.model.UserAccountSearchingForm;
import com.vn.service.AuthUserService;
import com.vn.service.InfomationService;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Resource
    private AuthUserService authUserService;

    @Resource
    private InfomationService infomationService;

    private byte TYPE = 2;

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String list(Model model, HttpSession session, HttpServletRequest request, Pageable pageable,
                       @RequestParam(value = "user_list_ss", defaultValue = "") String user_list_ss,
                       @ModelAttribute(value = "userSearchingForm") @Valid UserAccountSearchingForm userSearchingForm,
                       BindingResult bindingResult) {
        String not_found_message = "";
        model.addAttribute("userSearchingForm", userSearchingForm);
        if (bindingResult.hasErrors()) {
            Page<AuthUserModel> pageTop = new PageImpl<>(new ArrayList<AuthUserModel>());
            model.addAttribute("page", pageTop);
            return "admin/users/list";
        }

        if (request.getMethod().equalsIgnoreCase("GET")) {
            String userListSs = (String) session.getAttribute("user_list_ss");
            userSearchingForm = (UserAccountSearchingForm) session.getAttribute(userListSs);
            if (userSearchingForm == null) {
                userSearchingForm = new UserAccountSearchingForm();
            }
        }
        String userName = userSearchingForm.getUserName();
        String email = userSearchingForm.getEmail();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "userName"));
        Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
        Page<AuthUserModel> pageTop = authUserService.listUserByType(userName, email, TYPE, _pageable);
        if (request.getMethod().equalsIgnoreCase("POST") && pageTop.getContent().size() == 0) {
            not_found_message = "Không tìm thấy dữ liệu.";
        }
        Map<Byte, String> user_status = new HashedMap<>();
        user_status.put((byte) 0, "Tạm khóa");
        user_status.put((byte) 1, "Hoạt động");

        session.removeAttribute(user_list_ss);
        user_list_ss = UUID.randomUUID().toString();
        session.setAttribute("user_list_ss", user_list_ss);
        session.setAttribute(user_list_ss, userSearchingForm);
        request.getSession().setAttribute("pageIndex", pageable.getPageNumber());
        model.addAttribute("userSearchingForm", userSearchingForm);
        model.addAttribute("user_list_ss", user_list_ss);
        model.addAttribute("page", pageTop);
        model.addAttribute("user_status", user_status);
        model.addAttribute("not_found_message", not_found_message);
        return "admin/users/list";
    }

    @RequestMapping(value = "detail.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public @ResponseBody
    String detail(Model model, @RequestParam("id") Long id) throws Exception {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Infomation infomation = infomationService.findByAuthUserId(id);
            InfomationModel infomationModel = new InfomationModel();
            if (infomation != null) {
                infomationModel.setProvince(infomation.getProvince());
                infomationModel.setTown(infomation.getTown());
                infomationModel.setAddress(infomation.getAddress());
                infomationModel.setAtmNumberBank(infomation.getAtmNumber());
                infomationModel.setBank(infomation.getBank());
                infomationModel.setBirthday(infomation.getBirthday());
                infomationModel.setId(infomation.getId());
                infomationModel.setInfoPlus(infomation.getInfoPlus());
                infomationModel.setPhone(infomation.getPhone());
                infomationModel.setCompany(infomation.getCompany());
                infomationModel.setNameUser(infomation.getAuthUser().getFullName());
                infomationModel.setEmailUser(infomation.getAuthUser().getEmail());
            }
            return gson.toJson(infomationModel);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/user/list.html";
        }
    }

    @RequestMapping(value = "delete/{username}/list.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators')")
    public String deleteAccount(@PathVariable("username") String username) {
        AuthUser authUser = authUserService.findByUsername(username);
        if (authUser == null) {
            return "403";
        }
        authUserService.delete(authUser);
        return "redirect:/user/list.html";
    }
}
