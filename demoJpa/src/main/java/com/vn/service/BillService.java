package com.vn.service;

import com.vn.jpa.Bill;
import com.vn.model.BillProfileModel;
import com.vn.model.ChartDashboardBillOrder;
import com.vn.model.KeyValueStringIntegerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BillService {

    Page<Bill> findAll(Pageable pageable);

    Page<Bill> findAllBill(Date fromDate, Date toDate, Integer status,String code, String isDelete ,Pageable pageable);

    Bill insert(Bill bill);

    Bill update(Bill bill);

    void delete(Bill bill);

    Bill findOne(Long id);

    Bill findByCode(String code);

    List<Bill> findByTypeStatusAndMailStatus(Integer type, Integer statusMail);

    boolean checkExistByCode(String code);

    List<ChartDashboardBillOrder> listSumTotalForDashboard(Date date);

    Page<BillProfileModel> pageBillForShowProfileUser(Long id, Pageable pageable);
}
