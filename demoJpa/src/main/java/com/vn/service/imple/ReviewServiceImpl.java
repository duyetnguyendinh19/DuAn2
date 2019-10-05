package com.vn.service.imple;

import com.vn.jpa.Review;
import com.vn.repository.ReviewRepo;
import com.vn.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service(value = "reviewService")
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private ReviewRepo reviewRepo;

    @Override
    public Page<Review> findAll(Pageable pageable) {
        return reviewRepo.findAll(pageable);
    }

    @Override
    public List<Review> findAllByProductIdAndStatus(Long id, int status) {
        return reviewRepo.findAllByProductIdAndStatus(id, status);
    }

    @Override
    public Review insert(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public Review update(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public void delete(Review review) {
        reviewRepo.delete(review);
    }

    @Override
    public Review findOne(Long id) {
        return reviewRepo.findOne(id);
    }
}
