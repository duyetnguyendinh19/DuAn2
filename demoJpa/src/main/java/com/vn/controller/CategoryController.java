package com.vn.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.vn.validation.service.CategoryFormValidator;

@Controller
@RequestMapping("/category/")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

	@Resource
	private CategoryFormValidator categoryFormValidator;


    private String DELETE = "N";
    private String ISACTVE = "Y";

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
//    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    private String listCategory(Model model, Pageable pageable,
                                @RequestParam(value = "txtName", defaultValue = "") String txtName, HttpServletRequest request, HttpSession session) {
        String not_found_message = "";
        if (Strings.isNullOrEmpty(txtName) && request.getMethod().equalsIgnoreCase("GET")) {
            model.addAttribute("txtName", "");
        }
        if (request.getMethod().equalsIgnoreCase("POST")) {
            model.addAttribute("txtName", txtName);
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
        Page<Category> pageTop = categoryService.findAllCatePage(txtName, DELETE, ISACTVE, _pageable);
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
            model.addAttribute("title", "Thêm mới danh mục");
        } else {
            Category category = categoryService.findOne(id);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            model.addAttribute("category", category);
            if(category.getDate() == null) {
            	category.setDate(new Date());
            }
            model.addAttribute("date", sdf.format(category.getDate()));
            model.addAttribute("title", "Sửa danh mục");
        }

		model.addAttribute("lstCate", categoryService.findAllCateList(id, "", "N", "Y"));

        return "admin/categorys/cate_edit";
    }

	@RequestMapping(value = "save.html", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute(value = "category") @Valid Category category, BindingResult result,
			@RequestParam("date") String date, Model model) throws ParseException {
		categoryFormValidator.validateCategoryForm(category, result);
		if (result.hasErrors()) {
			if (category.getId() == null) {
				model.addAttribute("title", "Thêm mới danh mục");
			} else {
				model.addAttribute("title", "Sửa danh mục");
			}
			model.addAttribute("lstCate", categoryService.findAllCateList(category.getId() == null ? 0 : category.getId(), "", "N", "Y"));
			return "admin/categorys/cate_edit";
		}
		if (category.getId() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			category.setIsDelete("N");
			category.setDate(sdf.parse(date));
			categoryService.insert(category);
		} else {
			categoryService.update(category);
		}
		return "redirect:/category/list.html";
	}

}
