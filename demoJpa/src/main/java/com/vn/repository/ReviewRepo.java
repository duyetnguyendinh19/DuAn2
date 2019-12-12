package com.vn.repository;

import com.vn.jpa.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value = "reviewRepo")
public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findAllByProductIdAndStatus(Long id, int status);

    @Query(value = "SELECT rv FROM Review rv WHERE (rv.createDate BETWEEN :fromDate AND :toDate)" +
            "AND (:name IS NULL OR :name = '' OR rv.name LIKE CONCAT('%', :name, '%'))")
    Page<Review> findALlReview(@Param("fromDate") Date fromDate,
                               @Param("toDate") Date toDate,
                               @Param("name") String name,
                               Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM Review re WHERE (re.product.id = :id)")
    Long countRateByProductId(@Param("id") Long id);
    
    @Query(value = "SELECT COUNT(*) FROM Review re WHERE (re.bill.id = :id)")
    Long countRateByBillId(@Param("id") Long id);
}
