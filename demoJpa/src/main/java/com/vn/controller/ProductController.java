package com.vn.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.Constants;
import com.vn.common.FileUtils;
import com.vn.jpa.Category;
import com.vn.jpa.Product;
import com.vn.model.CategoryModel;
import com.vn.model.ProductModel;
import com.vn.service.CategoryService;
import com.vn.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.*;

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ProductService productService;

    @Value("${otoke.root.folder}")
    private String ROOT_FOLDER;

    @Value("${host.address}")
    private String HOST_ADDRESS;

    @Value("${main.images.folder}")
    private String MAIN_ADDRESS;

    @Value("${sub.images.folder}")
    private String SUB_ADDRESS;

//    private String MAIN_IMG_SOURCE_WEB = "/main-img/";
//    private String SUB_IMG_SOURCE_WEB = "/sub-img";

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
    public @ResponseBody
    String addProduct(@RequestBody(required = false) ProductModel model) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> responseMap = new HashMap<>();
        String filePathMain = ROOT_FOLDER + MAIN_ADDRESS;
        String sorceWebPathMain = HOST_ADDRESS + MAIN_ADDRESS;
        String filePathSub = ROOT_FOLDER + SUB_ADDRESS;
        String sorceWebPathSub = HOST_ADDRESS + SUB_ADDRESS;
        try {
            if (model.getIdCate() == null) {
                responseMap.put("idCate", "Danh mục sản phẩm không được để trống");
            }
            if (Strings.isNullOrEmpty(model.getName())) {
                responseMap.put("name", "Tên sản phẩm không được để trống");
            }
            if (!Strings.isNullOrEmpty(model.getName())) {
                if (productService.findAllByNameAndIsdelete(model.getName().trim(), "N") != null) {
                    responseMap.put("name", "Tên sản phẩm đã tồn tại");
                }
            }
            if (model.getQuantity() == null) {
                responseMap.put("quantity", "Số lượng không được để trống");
            }
            if (model.getPrice() == 0) {
                responseMap.put("price", "Giá sản phẩm không được để trống");
            }
            if (Strings.isNullOrEmpty(model.getInfo())) {
                responseMap.put("info", "Thông tin sản phẩm không được để trống");
            }
            if (responseMap.size() == 0) {
                FileUtils.Result resultMain = FileUtils.storageFile(filePathMain, model.getMainImg(), false, false, sorceWebPathMain);
                FileUtils.Result resultSub = FileUtils.storageFile(filePathSub, model.getSubImg(), false, false, sorceWebPathSub);
                Product product = new Product();
                Category category = new Category();
                category.setId(model.getIdCate());
                product.setIsdelete("N");
                product.setCategory(category);
                product.setDescription(model.getDescription());
                product.setInfo(model.getInfo());
                product.setName(model.getName());
                product.setPrice(model.getPrice());
                product.setPriceSale(model.getPriceSale());
                product.setQuantity(model.getQuantity());
                product.setStatus(1);
                product.setMainImg(resultMain.getResult());
                product.setSubImg(resultSub.getResult());
                productService.insert(product);
                responseMap.put("success", "Thêm sản phẩm thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(responseMap);
    }

    @RequestMapping(value = "categoryChildren.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public @ResponseBody
    String lsCategoryChildren(@RequestParam("parent_id") Long id) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/product/list.html";
        }
    }


    @RequestMapping(value = "{id}/update.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public String update(Model model, @PathVariable("id") Long id, @ModelAttribute("product") @Valid ProductModel productModel, BindingResult result) {
        Product product = productService.findOne(id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Category> category = categoryService.findByParentIdAndIsActiveAndIsDelete(null, ACTIVE, DELETE);
        List<CategoryModel> lsModel = new ArrayList<>();
        for (Category each : category) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(each.getId());
            categoryModel.setName(each.getName());
            lsModel.add(categoryModel);
        }
        model.addAttribute("category", lsModel);
        model.addAttribute("product", product);
        model.addAttribute("subImg", gson.toJson(product.getSubImg()));
        return "admin/product/update";
    }

    @RequestMapping(value = "{id}/update.html", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
    public @ResponseBody
    String updatePost(@PathVariable("id") Long id, @RequestBody(required = false) ProductModel model) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Product product = productService.findOne(id);
        Map<String, Object> responseMap = new HashMap<>();
        String filePathMain = ROOT_FOLDER + MAIN_ADDRESS;
        String sorceWebPathMain = HOST_ADDRESS + MAIN_ADDRESS;
        String filePathSub = ROOT_FOLDER + SUB_ADDRESS;
        String sorceWebPathSub = HOST_ADDRESS + SUB_ADDRESS;
        try {
            if (model.getIdCate() == null) {
                responseMap.put("idCate", "Danh mục sản phẩm không được để trống");
            }
            if (Strings.isNullOrEmpty(model.getName())) {
                responseMap.put("name", "Tên sản phẩm không được để trống");
            }
            if (!Strings.isNullOrEmpty(model.getName())) {
                if (!model.getName().trim().equals(product.getName())) {
                    if(productService.findAllByNameAndIsdelete(model.getName(), "N") != null){
                        responseMap.put("name", "Tên sản phẩm đã tồn tại");
                    }
                }
            }
            if (model.getQuantity() == null) {
                responseMap.put("quantity", "Số lượng không được để trống");
            }
            if (model.getPrice() == 0L) {
                responseMap.put("price", "Giá sản phẩm không được để trống");
            }
            if (Strings.isNullOrEmpty(model.getInfo())) {
                responseMap.put("info", "Thông tin sản phẩm không được để trống");
            }
            if(model.getSubImg() == null && model.getSubImg().length < 0 && product.getSubImg() == null){
                responseMap.put("sub", "Ảnh chi tiết không được để trống");
            }
            if (responseMap.size() == 0) {
                if (model.getMainImg() != null) {
                    String mainImg = product.getMainImg();
                    boolean isSaveSuccess = true;
                    if (!Strings.isNullOrEmpty(mainImg)) {
                        mainImg = FileUtils.changeWebSourcePathToFilePath(mainImg, filePathMain, sorceWebPathMain);
                        boolean deleteMain = FileUtils.deleteSingleImage(mainImg);
                        if (deleteMain) {
                            mainImg = "";
                        }
                    }
                    FileUtils.Result resultMain = FileUtils.storageFile(filePathMain, model.getMainImg(), false, false, sorceWebPathMain);
                    product.setMainImg(resultMain.getResult());
                }
                String oldSubImg = product.getSubImg();
                if (!Strings.isNullOrEmpty(model.getRemoveSub())) {
                    List<String> lsSubDelete = FileUtils.getImgPathFromString(model.getRemoveSub());
                    for (String str : lsSubDelete) {
                        if (!Strings.isNullOrEmpty(str)) {
                            boolean isDelete = false;
                            if (oldSubImg.contains("\"" + str + "\"" + ",")) {
                                oldSubImg = oldSubImg.replace("\"" + str + "\"" + ",", "");
                                isDelete = true;
                            } else if (oldSubImg.contains("," + "\"" + str + "\"") && !isDelete) {
                                oldSubImg = oldSubImg.replace("," + "\"" + str + "\"", "");
                                isDelete = true;
                            } else if (oldSubImg.contains("\"" + str + "\"") && !isDelete) {
                                oldSubImg = oldSubImg.replace("\"" + str + "\"", "");
                                isDelete = true;
                            }
                            if (isDelete) {
                                str = FileUtils.changeWebSourcePathToFilePath(str, filePathSub, sorceWebPathSub);
                                FileUtils.deleteSingleImage(str);
                            }
                        }
                    }
                }
                if (model.getSubImg() != null && model.getSubImg().length > 0) {
                    FileUtils.Result resultSub = FileUtils.storageFile(filePathSub, model.getSubImg(), false, false, sorceWebPathSub);
                    if (resultSub.isSuccess()) {
                        String subImgs = resultSub.getResult();
                        if (Strings.isNullOrEmpty(oldSubImg)) {
                            oldSubImg = subImgs;
                        } else {
                            if (subImgs.startsWith("[")) {
                                subImgs = subImgs.substring(1);
                            }
                            if (oldSubImg.endsWith("]")) {
                                oldSubImg = oldSubImg.substring(0, oldSubImg.length() - 1);
                            }
                            if (oldSubImg.trim().length() <= 1) {
                                oldSubImg = "[" + subImgs;
                            } else {
                                oldSubImg = oldSubImg + "," + subImgs;
                            }
                        }
                    }
                }
                product.setSubImg(oldSubImg);
                Category category = new Category();
                category.setId(model.getIdCate());
                product.setCategory(category);
                product.setDescription(model.getDescription());
                product.setInfo(model.getInfo());
                product.setName(model.getName());
                product.setPrice(model.getPrice());
                product.setPriceSale(model.getPriceSale());
                product.setQuantity(model.getQuantity());
                product.setStatus(model.getStatus());
                productService.update(product);
                responseMap.put("success", "Cập nhật thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(responseMap);
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
