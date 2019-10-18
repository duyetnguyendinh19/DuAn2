package com.vn.service;

import com.vn.jpa.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface BillService {

    Page<Bill> findAll(Pageable pageable);

    Page<Bill> findAllBill(Date fromDate, Date toDate, Integer status,String isDelete ,Pageable pageable);

    Bill insert(Bill bill);

    Bill update(Bill bill);

    void delete(Bill bill);

    Bill findOne(Long id);

    boolean checkExistByCode(String code);
}
