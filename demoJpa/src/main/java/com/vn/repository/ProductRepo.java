package com.vn.repository;

import com.vn.jpa.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "productRepo")
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM Product ORDER BY id DESC LIMIT 10",nativeQuery = true)
    List<Product> lsProductDateDesc();

    List<Product> findProductByCategoryId(Long id);

}
