package com.vn.service;

import com.vn.jpa.BankInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankInfoService {

    Page<BankInfo> findAll(Pageable pageable);

    BankInfo insert(BankInfo bankInfo);

    BankInfo update(BankInfo bankInfo);

    void delete(BankInfo bankInfo);

    BankInfo findOne(Long id);

}
