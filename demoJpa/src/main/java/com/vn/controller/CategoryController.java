package com.vn.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vn.common.Constants;
import com.vn.jpa.Category;
import com.vn.service.CategoryService;

@Controller
@RequestMapping("/category/")
public class CategoryController {

	@Resource
	private CategoryService categoryService;

	@RequestMapping(value = "list.html")
//	@PreAuthorize("hasAnyAuthority('Administrators')")
	private String listCategory(Model model, Pageable pageable,
			@RequestParam(value = "txtName", defaultValue = "") String txtName, HttpServletRequest request) {
		String not_found_message = "";
		if (request.getMethod().equalsIgnoreCase("GET")) {
			model.addAttribute("txtName", "");
		}
		if (request.getMethod().equalsIgnoreCase("POST")) {
			model.addAttribute("txtName", txtName);
		}
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
		Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
		Page<Category> pageTop = categoryService.findAllCatePage( txtName, "N", "Y", _pageable);
		if (pageTop.getContent().size() == 0) {
			not_found_message = "Không tìm thấy dữ liệu";
		}
		model.addAttribute("page", pageTop);
		model.addAttribute("not_found_message", not_found_message);
		return "admin/categorys/cate_list";
	}

	@RequestMapping(value = "delete/{id}/list.html", method = RequestMethod.GET)
//    @PreAuthorize("hasAnyAuthority('Administrators')")
	public String deleteCategory(@PathVariable("id") long id) {
		Category category = categoryService.findOne(id);
		if (category == null) {
			return "403";
		}
		category.setIsDelete("Y");
		categoryService.update(category);
		return "redirect:/category/list.html";
	}

	@RequestMapping(value = "{id}/edit.html", method = RequestMethod.GET)
	public String editCategory(Model model, @PathVariable("id") long id) {
		if (id == 0) {
			Category category = new Category();
			category.setIsActive("Y");
			model.addAttribute("category", category);
		} else {
			Category category = categoryService.findOne(id);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			model.addAttribute("category", category);
			model.addAttribute("date", sdf.format(category.getDate()));
		}

		model.addAttribute("lstCate", categoryService.findAllCateList(id, "", "N", "Y"));

		return "admin/categorys/cate_edit";
	}

	@RequestMapping(value = "save.html", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute(value = "category") @Valid Category category, BindingResult result,@RequestParam("date") String date) throws ParseException {
		if(category.getId() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			category.setIsDelete("N");
			category.setDate(sdf.parse(date));
			categoryService.insert(category);
		}else {
			categoryService.update(category);
		}
		return "redirect:/category/list.html";
	}

}
