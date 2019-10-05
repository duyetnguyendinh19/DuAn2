package com.vn.repository;

import com.vn.jpa.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "reviewRepo")
public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long id);
}
