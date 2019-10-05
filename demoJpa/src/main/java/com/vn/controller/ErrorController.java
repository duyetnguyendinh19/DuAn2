package com.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        return "404";
    }

    @RequestMapping(value = "/error/403.html", method = RequestMethod.GET)
    public String denied() {
        return "403";
    }

    @RequestMapping(value = "/404.html", method = RequestMethod.GET)
    public String notfound() {
        return "404";
    }

}
