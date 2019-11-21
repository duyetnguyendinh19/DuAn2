package com.vn.repository;

import com.vn.jpa.Product_Bill;
import com.vn.model.KeyValueStringIntegerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value = "product_billRepo")
public interface Product_BillRepo extends JpaRepository<Product_Bill, Long> {

    @Query( value= "SELECT SUM(pb.quantity) FROM Product_Bill pb WHERE (pb.product.id = :id) GROUP BY pb.product.id", nativeQuery =  false)
    Long countQuantityByProduct(@Param("id") Long id);

    List<Product_Bill> findByProductId(Long id);

    List<Product_Bill> findByBill_Id(Long id);

    @Query(value = "SELECT pb.created_date, COUNT(id) FROM product_bill pb " +
            "WHERE (pb.isdelete = 'N') AND (pb.created_date BETWEEN DATE(:fromDate) AND DATE(:toDate))" +
            "GROUP BY DATE(pb.created_date)", nativeQuery = true)
    List<Object[]> listCountBillGrByDateBillId(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);
}
