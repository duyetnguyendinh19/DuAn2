package com.vn.service.imple;

import com.vn.jpa.Bill;
import com.vn.repository.BillRepo;
import com.vn.service.BillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

@Service(value = "billService")
@Transactional
public class BillServiceImpl implements BillService {

    @Resource
    private BillRepo billRepo;

    @Override
    public Page<Bill> findAll(Pageable pageable) {
        return billRepo.findAll(pageable);
    }

    @Override
    public Page<Bill> findAllBill(Date fromDate, Date toDate, Integer status,String isDelete, Pageable pageable) {
        return billRepo.findAllBillParam(fromDate, toDate, status,isDelete, pageable);
    }

    @Override
    public Bill insert(Bill bill) {
        return billRepo.save(bill);
    }

    @Override
    public Bill update(Bill bill) {
        return billRepo.save(bill);
    }

    @Override
    public void delete(Bill bill) {
        billRepo.delete(bill);
    }

    @Override
    public Bill findOne(Long id) {
        return billRepo.findOne(id);
    }
}
