package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vn.common.Constants;
import com.vn.jpa.AuthUser;
import com.vn.jpa.Category;
import com.vn.jpa.Infomation;
import com.vn.jpa.Product;
import com.vn.jpa.Report;
import com.vn.jpa.Review;
import com.vn.model.AuthUserModel;
import com.vn.model.InfomationModel;
import com.vn.model.ProductQuickViewModel;
import com.vn.service.AuthUserService;
import com.vn.service.CategoryService;
import com.vn.service.InfomationService;
import com.vn.service.ProductService;
import com.vn.service.ReviewService;
import com.vn.validation.service.InfomationFormValidator;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
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

import java.util.*;

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Resource
	private JavaMailSender mailSender;

	@Resource
	private InfomationFormValidator infoFormValidator;

	@Resource
	private InfomationService informationService;

	@RequestMapping(value = "/home/login.html", method = RequestMethod.GET)
	public ModelAndView loginPage(Model model, Pageable pageable) {
		Map<String, String> mapError = new HashedMap<String, String>();
		model.addAttribute("athUser", new AuthUserModel());
		model.addAttribute("mapError", mapError);
		ModelAndView modelAndView = new ModelAndView("home/login");
		return modelAndView;
	}

	@RequestMapping(value = "/home/login.html", method = RequestMethod.POST)
	public String loginPageSuccsess(HttpSession session, @RequestParam("user") String user,
			@RequestParam("password") String pass, Model model) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
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

	@RequestMapping(value = "/home/{id}/single-product.html", method = { RequestMethod.GET })
	public ModelAndView singleProduct(Model model, @PathVariable("id") Long id) {
		Product product = productService.findOne(id);
		List<Product> newProduct = productService.lsProductDateDesc();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		if (product.getSubImg() != null || product.getSubImg() != "") {
			String[] subImg = gson.fromJson(product.getSubImg(), new TypeToken<String[]>() {
			}.getType());
			model.addAttribute("subImg", subImg);
		}
		List<Review> lsReview = reviewService.findAllByProductIdAndStatus(product.getId(),
				Review.status.ACTIVE.value());
		List<Product> productRelationship = productService.findProductByCategoryId(product.getCategory().getId());
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("product", product);
		model.addAttribute("proRelationship", productRelationship);
		model.addAttribute("review", lsReview);
		ModelAndView modelAndView = new ModelAndView("home/product-single");
		return modelAndView;
	}

	@RequestMapping(value = "/home/{id}/product.html", method = { RequestMethod.GET })
	public String product(Model model, @PathVariable("id") Long id, Pageable pageable) {
		Category category = categoryService.findOne(id);
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		Pageable _page = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
		Page<Product> page = productService.findAllByCategoryIdAndIsdelete(id, _page, "N");
		model.addAttribute("name", category.getName());
		model.addAttribute("page", page);
		return "home/product";
	}

	@RequestMapping(value = "/home/single-product.html", method = RequestMethod.GET)
	public @ResponseBody String category(@RequestParam("id") Long id) {
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
		return gson.toJson(viewModel);
	}

	@RequestMapping(value = "/home/cart.html", method = RequestMethod.GET)
	public ModelAndView viewCart() {

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
		if (session.getAttribute("userLogin") == null) {
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
				informationService.create(infomation);
			} else {
				informationService.update(infomation);
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
		return "redirect:/home/login.html";
	}

	@RequestMapping(value = "sendMail.html", method = RequestMethod.POST)
	public ModelAndView sendMailTest(Model model, @RequestParam("mail") String mail) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
//        String html = "<h2>Xin chào, My name is Tấn Test Mail</h2>";
//        message.setContent(html,"text/html; charset=UTF-8"); // content mail
//        mimeMessageHelper.setTo(mail);
//        mimeMessageHelper.setSubject("Test mail ngày " + sdf.format(date)); // tiêu đề
//        mailSender.send(message);
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		return modelAndView;
	}
}
