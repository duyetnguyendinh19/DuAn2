package com.vn.service;

import com.vn.jpa.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ReviewService {

    Page<Review> findAll(Pageable pageable);

    Page<Review> findALlReview(Date fromDate, Date toDate, String name, Pageable pageable);

    List<Review> findAllByProductIdAndStatus(Long id, int status);
    
    Long countRateByBillId(Long i);

    Review insert(Review review);

    Review update(Review review);

    void delete(Review review);

    Review findOne(Long id);

    Long countRateByProductId(Long id);

}
