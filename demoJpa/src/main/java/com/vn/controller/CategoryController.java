package com.vn.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vn.common.Constants;
import com.vn.model.CategoryModel;
import com.vn.service.CategoryService;

@Controller
@RequestMapping("/category/")
public class CategoryController {

	private CategoryService categoryService;

	@RequestMapping(value = "list.html")
	@PreAuthorize("hasAnyAuthority('Administrators')")
	private String listCategory(Model model, Pageable pageable, @PathVariable("txtName") String txtName,
			BindingResult bindingResult) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "name"));
		Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
		Page<CategoryModel> pageTop = categoryService.getList(_pageable);
		model.addAttribute("page", pageTop);
		model.addAttribute("txtName", "");
		System.out.println();
		return "admin/categorys/cate_list";
	}

}
