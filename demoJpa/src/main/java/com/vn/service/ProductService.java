package com.vn.service;

import com.vn.jpa.Category;
import com.vn.jpa.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ProductService {

    Page<Product> findAllByIsdelete(String isdelete, Pageable pageable);

    Product insert(Product product);

    Product update(Product product);

    void delete(Product product);

    Product findOne(Long id);

    List<Product> lsProductDateDesc();

    List<Product> findProductByCategoryIdAndIsdelete(Long id, String isdelete);

    Page<Product> findAllByCategoryIdAndIsdelete(Long id, Pageable pageable, String isdelete);

    Page<Product> findAllProduct(Date fromDate, Date toDate, String name,String isDelete, Pageable pageable);

    Product findAllByNameAndIsdelete(String name, String isdelete);

    Page<Product> findByCategoryParentAndIsdelete(List<Long> categoryId, String isdelete,Pageable pageable);

    Page<Product> findAllByNameIsLikeAndIsdelete(String name,String isdelete, Pageable pageable);
}
