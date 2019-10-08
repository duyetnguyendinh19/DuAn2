package com.vn.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vn.common.Constants;
import com.vn.jpa.AuthUser;
import com.vn.jpa.Category;
import com.vn.model.CategoryModel;
import com.vn.service.CategoryService;

@Controller
@RequestMapping("/category/")
public class CategoryController {

	@Resource
	private CategoryService categoryService;

	@RequestMapping(value = "list.html")
//	@PreAuthorize("hasAnyAuthority('Administrators')")
	private String listCategory(Model model, Pageable pageable) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
		Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
		Page<Category> pageTop = categoryService.findAll(_pageable);
		model.addAttribute("page", pageTop);
		model.addAttribute("txtName", "");
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
	
}
