package com.vn.repository;

import com.vn.jpa.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository(value = "billRepo")
public interface BillRepo extends JpaRepository<Bill, Long> {

    @Query(value = "SELECT b FROM Bill b WHERE (b.createDate BETWEEN :fromDate AND :toDate)" +
            "AND (:status = 0 OR b.status = :status)" +
            "AND (b.isDelete = :delete)", nativeQuery = false)
    Page<Bill> findAllBillParam(@Param("fromDate") Date fromDate,
                           @Param("toDate") Date toDate,
                           @Param("status") Integer status,
                           @Param("delete") String isDelete,
                           Pageable pageable);

}
