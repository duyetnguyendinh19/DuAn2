package com.vn.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.Constants;
import com.vn.common.FileUtils;
import com.vn.common.FileUtils.DescriptionBase64File;
import com.vn.jpa.Category;
import com.vn.jpa.Product;
import com.vn.model.CategoryModel;
import com.vn.model.ProductModel;
import com.vn.service.CategoryService;
import com.vn.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ProductService productService;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private String DELETE = "N";
    private String ACTIVE = "Y";

    @RequestMapping(value = "list.html", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String list(Model model, HttpSession session, HttpServletRequest request,
                       @RequestParam(value = "from_date", defaultValue = "") String fromDate,
                       @RequestParam(value = "to_date", defaultValue = "") String toDate,
                       @RequestParam(value = "name", defaultValue = "") String name,
                       Pageable pageable) {
        try {
            DateTime time = new DateTime();
            Date _fromDate = time.withTimeAtStartOfDay().toDate();
            Date _toDate = time.withTime(23, 59, 59, 999).toDate();

            if (Strings.isNullOrEmpty(fromDate) && request.getMethod().equalsIgnoreCase("GET")) {
                fromDate = (String) session.getAttribute("from_date");
                if (Strings.isNullOrEmpty(fromDate)) {
                    fromDate = sdf.format(_fromDate);
                }
            }
            if (Strings.isNullOrEmpty(toDate) && request.getMethod().equalsIgnoreCase("GET")) {
                toDate = (String) session.getAttribute("to_date");
                if (Strings.isNullOrEmpty(toDate)) {
                    toDate = sdf.format(_toDate);
                }
            }
            if (Strings.isNullOrEmpty(name) && request.getMethod().equalsIgnoreCase("GET")) {
                name = (String) session.getAttribute("name");
            }
            try {
                _fromDate = sdf.parse(fromDate);
                _toDate = sdf.parse(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.addAttribute("from_date", fromDate);
            model.addAttribute("to_date", toDate);
            model.addAttribute("name", name);
            if (Strings.isNullOrEmpty(name)) {
                name = "";
            }
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
            Page<Product> page = productService.findAllProduct(_fromDate, _toDate, name, DELETE, _pageable);
            model.addAttribute("page", page);
            session.setAttribute("from_date", sdf.format(_fromDate));
            session.setAttribute("to_date", sdf.format(_toDate));
            request.getSession().setAttribute("pageIdx", pageable.getPageNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/product/list";
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String add(Model model) {
        List<Category> category = categoryService.findByParentIdAndIsActiveAndIsDelete(null, ACTIVE, DELETE);
        List<CategoryModel> lsModel = new ArrayList<>();
        for (Category each : category) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(each.getId());
            categoryModel.setName(each.getName());
            lsModel.add(categoryModel);
        }
        model.addAttribute("category", lsModel);
        return "admin/product/add";
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String addProduct(@RequestBody(required = false) ProductModel model) {
        try {
            Product product = new Product();
            FileUtils.Result result = FileUtils.storageFile("",model.getMainImg(),false,false);
            product.setMainImg(result.getResult());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/product/list.html";
    }

    @RequestMapping(value = "categoryChildren.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public @ResponseBody String lsCategoryChildren(@RequestParam("parent_id") Long id){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Category> category = categoryService.findByParentIdAndIsActiveAndIsDelete(id, ACTIVE, DELETE);
            List<CategoryModel> lsModel = new ArrayList<>();
            for (Category each : category) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(each.getId());
                categoryModel.setName(each.getName());
                lsModel.add(categoryModel);
            }
            return gson.toJson(lsModel);
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/product/list.html";
        }
    }


    @RequestMapping(value = "{id}/update.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String update(Model model, @PathVariable("id") Long id, @ModelAttribute("product") @Valid ProductModel productModel, BindingResult result) {
        Product product = productService.findOne(id);
        return "admin/product/update";
    }

    @RequestMapping(value = "delete/{id}/list.html")
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String updateisDelete(Model model, @PathVariable("id") Long id) {
        try {
            Product product = productService.findOne(id);
            product.setIsdelete(Constants.ISDELETE);
            productService.update(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/product/list.html";
    }
}
