package com.vn.service;

import com.vn.jpa.Reject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RejectService {

    Page<Reject> findAll(Pageable pageable);

    Reject insert(Reject reject);

    Reject update(Reject reject);

    void delete(Reject reject);

    Reject findOne(Long id);
}
