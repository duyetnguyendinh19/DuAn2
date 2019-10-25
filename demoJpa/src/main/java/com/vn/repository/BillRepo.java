package com.vn.repository;

import com.vn.jpa.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value = "billRepo")
public interface BillRepo extends JpaRepository<Bill, Long> {

    @Query(value = "SELECT b FROM Bill b WHERE (b.createDate BETWEEN :fromDate AND :toDate)" +
            "AND (:status = 0 OR b.status = :status)" +
            "AND (:code IS NULL OR :code = '' OR b.code = :code)" +
            "AND (b.isDelete = :delete)", nativeQuery = false)
    Page<Bill> findAllBillParam(@Param("fromDate") Date fromDate,
                           @Param("toDate") Date toDate,
                           @Param("status") Integer status,
                           @Param("code") String code,
                           @Param("delete") String isDelete,
                           Pageable pageable);

    @Query(value = "SELECT b.id FROM Bill b "
            + " WHERE (b.code = :code)"
            , nativeQuery = false)
    Long checkExistByCode(@Param(value = "code") String code);

    Bill findByCode(String code);

    List<Bill> findByTypeStatusAndMailStatus(Integer type, Integer statusMail);
}
