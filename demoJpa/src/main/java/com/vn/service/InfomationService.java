package com.vn.service;

import com.vn.jpa.Infomation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InfomationService {

    Page<Infomation> findAll(Pageable pageable);

    Infomation create(Infomation infomation);

    Infomation update(Infomation infomation);

    void delete(Infomation infomation);

    Infomation findOne(Long id);

    Infomation findByAuthUserId(Long id);
    
    Infomation findByPhone(String phone);
}
