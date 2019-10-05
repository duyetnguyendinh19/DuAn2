package com.vn.service;

import com.vn.jpa.Category;
import com.vn.jpa.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);


    Product insert(Product product);

    Product update(Product product);

    void delete(Product product);

    Product findOne(Long id);

    List<Product> lsProductDateDesc();

    List<Product> findProductByCategoryId(Long id);

}
