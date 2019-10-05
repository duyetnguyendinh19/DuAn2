package com.vn.service;

import com.vn.jpa.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {

    Page<Bill> findAll(Pageable pageable);

    Bill insert(Bill bill);

    Bill update(Bill bill);

    void delete(Bill bill);

    Bill findOne(Long id);

}
