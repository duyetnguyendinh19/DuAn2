package com.vn.service;

import com.vn.jpa.Reject;
import com.vn.model.ChartDashboardBillOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface RejectService {

    Page<Reject> findAll(Pageable pageable);

    Reject insert(Reject reject);

    Reject update(Reject reject);

    void delete(Reject reject);

    Reject findOne(Long id);

    List<ChartDashboardBillOrder> listCountRejectDashBoard(Date date);

    Page<Reject> pageByDateAndCode(Date fromDate, Date toDate, String code, Pageable pageable);
}
