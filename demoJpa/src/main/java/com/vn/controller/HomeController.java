package com.vn.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.util.StringUtils;
import com.vn.common.Constants;
import com.vn.common.GoogleUtils;
import com.vn.common.ThymeleafUtil;
import com.vn.config.GoogleMailSender;
import com.vn.jpa.*;
import com.vn.model.AuthUserModel;
import com.vn.model.InfomationModel;
import com.vn.model.ProductQuickViewModel;
import com.vn.service.*;

import com.vn.validation.service.InfomationFormValidator;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

@Controller
public class HomeController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ProductService productService;

    @Resource
    private ReviewService reviewService;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private InfomationService infomationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private InfomationFormValidator infoFormValidator;

    @Resource
    private GmailGoogleService gmailGoogleService;

    @RequestMapping(value = "/home/login.html", method = RequestMethod.GET)
    public ModelAndView loginPage(Model model, Pageable pageable) {
        Map<String, String> mapError = new HashedMap<String, String>();
        model.addAttribute("athUser", new AuthUserModel());
        model.addAttribute("mapError", mapError);
        ModelAndView modelAndView = new ModelAndView("home/login");
        return modelAndView;
    }

    @RequestMapping(value = "/home/login-google", method = {RequestMethod.GET})
    public String loginGoogle(@RequestParam("code") String code, HttpSession session) throws IOException {
        if (code == null || code.isEmpty()) {
            return "home/login";
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GmailGoogle googlePojo = GoogleUtils.getUserInfo(accessToken);
            try {
                gmailGoogleService.insert(googlePojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.setAttribute("userGoogle", googlePojo);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/home/login.html", method = RequestMethod.POST)
    public String loginPageSuccsess(HttpSession session, @RequestParam("user") String user, @RequestParam("password") String pass, Model model) {
        AuthUser authUser = authUserService.findByUsername(user);
        if (authUser != null) {
            if (passwordEncoder.matches(pass, authUser.getPassword())) {
                session.setAttribute("userLogin", authUser);
                return "redirect:/";
            }
        }
        Map<String, String> mapError = new HashedMap<String, String>();
        model.addAttribute("athUser", new AuthUserModel());
        model.addAttribute("mapError", mapError);
        model.addAttribute("errorLogin", "Sai tài khoản hoặc mật khẩu");
        return "home/login";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView index(Model model, Pageable pageable, HttpSession session) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable _pageable = new PageRequest(pageable.getPageNumber(), 8, sort);
        Page<Product> product = productService.findAllByIsdelete("N", _pageable);
        List<Product> newProduct = productService.lsProductDateDesc();
        List<Category> category = categoryService.findAllByIsDeleteAndIsActive("N", "Y");
        Map<Long, List> mapLsId = new HashMap<>();
        for (Category each : category) {
            if (each.getParent() == null) {
                List ls = new ArrayList();
                ls.add(each.getName());
                List lsCategoryChildren = new ArrayList();
                ls.add(lsCategoryChildren);
                mapLsId.put(each.getId(), ls);
            }
        }
        for (Category eachCateChildren : category) {
            if (eachCateChildren.getParent() != null) {
                ArrayList lsChildren = (ArrayList) mapLsId.get(eachCateChildren.getParent().getId()).get(1);
                List lsCategoryInfo = new ArrayList();
                lsCategoryInfo.add(eachCateChildren.getId());
                lsCategoryInfo.add(eachCateChildren.getName());
                lsChildren.add(lsCategoryInfo);
            }
        }
        session.setAttribute("categoryNav", mapLsId);
        model.addAttribute("newProduct", newProduct);
        model.addAttribute("page", product);
        ModelAndView modelAndView = new ModelAndView("home/index");
        return modelAndView;
    }

    @RequestMapping(value = "/home/{id}/single-product.html", method = {RequestMethod.GET})
    public ModelAndView singleProduct(Model model, @PathVariable("id") Long id) {
        Product product = productService.findOne(id);
        List<Product> newProduct = productService.lsProductDateDesc();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (product.getSubImg() != null || product.getSubImg() != "") {
            String[] subImg = gson.fromJson(product.getSubImg(), new TypeToken<String[]>() {
            }.getType());
            model.addAttribute("subImg", subImg);
        }
        List<Review> lsReview = reviewService.findAllByProductIdAndStatus(product.getId(), Review.status.ACTIVE.value());
        List<Product> productRelationship = productService.findProductByCategoryIdAndIsdelete(product.getCategory().getId(), "N");
        Long countRate = reviewService.countRateByProductId(id);
        model.addAttribute("countRate", countRate);
        model.addAttribute("newProduct", newProduct);
        model.addAttribute("product", product);
        model.addAttribute("proRelationship", productRelationship);
        model.addAttribute("review", lsReview);
        ModelAndView modelAndView = new ModelAndView("home/product-single");
        return modelAndView;
    }

    @RequestMapping(value = "/home/{id}/product.html", method = {RequestMethod.GET})
    public String product(Model model, @PathVariable("id") Long id, Pageable pageable) {
        Category category = categoryService.findOne(id);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable _page = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
        Page<Product> page = productService.findAllByCategoryIdAndIsdelete(id, _page, "N");
        model.addAttribute("name", category.getName());
        model.addAttribute("page", page);
        return "home/product";
    }

    @RequestMapping(value = "/home/{id}/list/product.html", method = {RequestMethod.GET})
    public String listProduct(Model model, @PathVariable("id") Long parentId, Pageable pageable) {
        try {
            List<BigInteger> lsCate = categoryService.getListCategoryById(parentId, "Y", "N");
            List<Long> lsLong = new ArrayList<>();
            for (BigInteger each : lsCate) {
                lsLong.add(each.longValue());
            }
            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
            Pageable _page = new PageRequest(pageable.getPageNumber(), 8, sort);
            Page<Product> page = productService.findByCategoryParentAndIsdelete(lsLong, "N", _page);
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home/product";
    }

    @RequestMapping(value = "/home/single-product.html", method = RequestMethod.GET)
    public @ResponseBody
    String category(@RequestParam("id") Long id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Product product = productService.findOne(id);
        ProductQuickViewModel viewModel = new ProductQuickViewModel();
        viewModel.setDescription(product.getDescription());
        viewModel.setName(product.getName());
        viewModel.setPrice(product.getPrice());
        viewModel.setPriceSale(product.getPriceSale());
        viewModel.setMainImg(product.getMainImg());
        viewModel.setStatus(product.getStatus());
        viewModel.setInfo(product.getInfo());
        viewModel.setQuantity(product.getQuantity());
        return gson.toJson(viewModel);
    }

    @RequestMapping(value = "/home/cart.html", method = RequestMethod.GET)
    public ModelAndView viewCart(HttpSession session, Model model) {
        AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
        GmailGoogle gmailGoogle = (GmailGoogle) session.getAttribute("userGoogle");
        if (authUser != null) {
            Infomation infomation = infomationService.findByAuthUserId(authUser.getId());
            model.addAttribute("name", authUser.getFullName());
            model.addAttribute("email", authUser.getEmail());
            if (infomation != null) {
                model.addAttribute("mobile", infomation.getPhone());
                model.addAttribute("address", infomation.getAddress());
            }
        }
        if (gmailGoogle != null) {
            model.addAttribute("name", gmailGoogle.getName());
            model.addAttribute("email", gmailGoogle.getEmail());
        }
        ModelAndView modelAndView = new ModelAndView("home/cart");
        return modelAndView;
    }

    @RequestMapping(value = "/home/about.html", method = RequestMethod.GET)
    public ModelAndView viewAbout() {
        ModelAndView modelAndView = new ModelAndView("home/about");
        return modelAndView;
    }

    @RequestMapping(value = "/home/contact.html", method = RequestMethod.GET)
    public ModelAndView viewContact(Model model) {
        model.addAttribute("report", new Report());
        model.addAttribute("mapError", new HashedMap<String, String>());
        ModelAndView modelAndView = new ModelAndView("home/contact");
        return modelAndView;
    }

    @RequestMapping(value = "/home/profile.html", method = RequestMethod.GET)
    public ModelAndView profilePage(HttpSession session, Model model) {
        if (session.getAttribute("userLogin") == null && session.getAttribute("userGoogle") == null) {
            Map<String, String> mapError = new HashedMap<String, String>();
            model.addAttribute("athUser", new AuthUserModel());
            model.addAttribute("mapError", mapError);
            ModelAndView modelAndView = new ModelAndView("home/login");
            return modelAndView;
        } else {
            model.addAttribute("profile", new InfomationModel());
            ModelAndView modelAndView = new ModelAndView("home/profile");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/home/profile.html", method = RequestMethod.POST)
    public ModelAndView profileSave(HttpSession session, Model model,
                                    @ModelAttribute("profile") @Valid InfomationModel infomationModel, BindingResult result) {
        try {
            infoFormValidator.validateReportForm(infomationModel, result);
            Infomation infomation = new Infomation();
            AuthUser authUser = authUserService.findOne(((AuthUser) session.getAttribute("userLogin")).getId());
            authUser.setEmail(infomationModel.getEmailUser());
            infomation.setProvince(infomationModel.getProvince());
            infomation.setTown(infomationModel.getTown());
            infomation.setBank(infomationModel.getBank());
            infomation.setAtmNumber(infomationModel.getAtmNumberBank());
            infomation.setCompany(infomationModel.getCompany());
            infomation.setPhone(infomation.getPhone());
            infomation.setAuthUser(authUser);
            infomation.setIsDelete("N");
            if (infomation.getId() == null) {
                infomationService.create(infomation);
            } else {
                infomationService.update(infomation);
            }
        } catch (Exception e) {
            model.addAttribute("errorUnkown", "Lỗi không xác định !");
        }
        ModelAndView modelAndView = new ModelAndView("home/profile");
        return modelAndView;
    }

    @RequestMapping(value = "/home/logout.html", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("userLogin");
        session.removeAttribute("userGoogle");
        return "redirect:/home/login.html";
    }

    @RequestMapping(value = "/home/{user}/reset.html", method = RequestMethod.GET)
    public ModelAndView resetPassword(Model model, @PathVariable("user") String user) {
        ModelAndView modelAndView = new ModelAndView("home/reset-password");
        return modelAndView;
    }

    @RequestMapping(value = "/home/reset.html", method = RequestMethod.POST)
    public @ResponseBody
    String reset(Model model, @RequestParam("user") String user, @RequestParam("oldPass") String oldPass,
                 @RequestParam("newPass") String newPass, HttpSession session) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> respone = new HashMap<>();
        try {
            AuthUser authUser = authUserService.findByUsername(user);
            if (authUser != null) {
                if (!passwordEncoder.matches(oldPass, authUser.getPassword())) {
                    respone.put("oldPassErr", "Nhập sai mật khẩu hiện tại");
                }
                if (respone.size() == 0) {
                    authUser.setPassword(passwordEncoder.encode(newPass));
                    authUserService.update(authUser);
                    respone.put("success", "Đổi mật khẩu thành công");
                    session.removeAttribute("userLogin");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(respone);
    }

    @RequestMapping(value = "/home/forgetPassword.html", method = RequestMethod.POST)
    public @ResponseBody
    String forgetPassword(@RequestParam("email") String email) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> response = new HashMap<>();
        try {
            AuthUser authUser = authUserService.findByEmail(email);
            if (authUser != null) {
                Map<String, Object> responseMap = new HashMap<>();
                String pass = RandomStringUtils.randomAlphanumeric(8);
                GoogleMailSender mailSender = new GoogleMailSender();
                if (authUser.getFullName() != null) {
                    responseMap.put("name", authUser.getFullName());
                } else {
                    responseMap.put("name", authUser.getUserName());
                }
                responseMap.put("password", pass);
                responseMap.put("userName", authUser.getUserName());
                new Thread(
                        () -> {
                            try {
                                final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailForgetPassword.html", (HashMap<String, Object>) responseMap);
                                mailSender.sendSimpleMailWarningTLS("ÔTôKê<tanbv.dev@gmail.com>", email, "[ÔTôKê] Cấp Lại Mật Khẩu", htmlContent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                ).start();
                authUser.setPassword(passwordEncoder.encode(pass));
                authUserService.update(authUser);
                response.put("success", "Quý khách vui lòng truy cập email để nhận lại mật khẩu mới");
            } else {
                response.put("err", "Không tìm thấy địa chỉ email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(response);
    }
}
