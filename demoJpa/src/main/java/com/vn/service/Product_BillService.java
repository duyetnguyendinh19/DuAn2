package com.vn.service;

import com.vn.jpa.Product_Bill;
import com.vn.model.ChartDashboardBillOrder;
import com.vn.model.KeyValueStringIntegerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface Product_BillService {

    Page<Product_Bill> findAll(Pageable pageable);

    Product_Bill insert(Product_Bill product_bill);

    Product_Bill update(Product_Bill product_bill);

    void delete(Product_Bill product_bill);

    Product_Bill findOne(Long id);

    Long countQuantityByProduct(Long id);

    List<Product_Bill> findByProductId(Long id);

    List<Product_Bill> findByBill_Id(Long id);

    List<ChartDashboardBillOrder> listCountBillGrByDateBillId(Date date);

    List<KeyValueStringIntegerModel> listCountBillOrGrDateNow();
}
