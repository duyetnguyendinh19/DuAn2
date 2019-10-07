package com.vn.controller;

import com.vn.jpa.Category;
import com.vn.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "login.html", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "admin/login";
    }

//    @PreAuthorize("hasAnyAuthority('Administrators')")
    @GetMapping(value = {"dashboard.html"})
    public String index(ModelMap modelMap, Pageable pageable, HttpSession session) {
//        Page<Category> page = categoryService.findAll(pageable);
//        modelMap.addAttribute("page", page);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        modelMap.addAttribute("name1", authentication.getName());
        return "admin/index";
    }

}
