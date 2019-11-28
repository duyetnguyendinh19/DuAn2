package com.vn.service;

import com.vn.jpa.BankInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankInfoService {

    Page<BankInfo> findAll(Pageable pageable);
    
    List<BankInfo> findAll();

    BankInfo insert(BankInfo bankInfo);

    BankInfo update(BankInfo bankInfo);

    void delete(BankInfo bankInfo);

    BankInfo findOne(Long id);

    List<BankInfo> findAllByType(Integer type);

}
