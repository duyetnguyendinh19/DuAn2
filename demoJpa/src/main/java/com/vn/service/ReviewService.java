package com.vn.service;

import com.vn.jpa.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    Page<Review> findAll(Pageable pageable);

    List<Review> findAllByProductIdAndStatus(Long id, int status);

    Review insert(Review review);

    Review update(Review review);

    void delete(Review review);

    Review findOne(Long id);
}
