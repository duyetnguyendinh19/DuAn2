package com.vn.repository;

import com.vn.jpa.Product_Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "product_billRepo")
public interface Product_BillRepo extends JpaRepository<Product_Bill, Long> {

}
