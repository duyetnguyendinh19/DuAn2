package com.vn.service.imple;

import com.vn.jpa.Bill;
import com.vn.model.BillProfileModel;
import com.vn.model.ChartDashboardBillOrder;
import com.vn.model.KeyValueStringIntegerModel;
import com.vn.repository.BillRepo;
import com.vn.service.BillService;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Page<Bill> findAllBill(Date fromDate, Date toDate, Integer status,String code, String isDelete, Pageable pageable) {
        return billRepo.findAllBillParam(fromDate, toDate, status,code, isDelete, pageable);
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

    @Override
    public Bill findByCode(String code) {
        return billRepo.findByCode(code);
    }

    @Override
    public List<Bill> findByTypeStatusAndMailStatus(Integer type, Integer statusMail) {
        return billRepo.findByTypeStatusAndMailStatus(type,statusMail);
    }

    @Override
    public boolean checkExistByCode(String code) {
        boolean result = false;
        Long id = billRepo.checkExistByCode(code);
        if (id != null && id > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public List<ChartDashboardBillOrder> listSumTotalForDashboard(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateTime time = new DateTime(date);
        Date fromDate = time.plusDays(-7).toDate();
        Date toDate = time.withTimeAtStartOfDay().toDate();
        List<ChartDashboardBillOrder> response = new ArrayList<>();
        List<Object[]> lsObject = billRepo.listSumTotalForDashboard(fromDate, toDate);
        for(Object[] each : lsObject){
            String key = sdf.format(each[0]);
            Double val = (Double) each[1];
            Integer value = val.intValue();
            ChartDashboardBillOrder model = new ChartDashboardBillOrder(key, value);
            response.add(model);
        }
        return response;
    }

    @Override
    public Page<BillProfileModel> pageBillForShowProfileUser(Long id, Pageable pageable) {
        List<BillProfileModel> response = new ArrayList<>();
        Page<Object[]> lsObject = billRepo.pageBillForShowProfileUser(id, pageable);
        for(Object[] each : lsObject){
            Date date = (Date) each[0];
            String code = (String) each[1];
            Float total = (Float) each[2];
            Integer typeStatus = (Integer) each[3];
            Integer status = (Integer) each[4];
            BillProfileModel model = new BillProfileModel(date, code, total, typeStatus, status);
            response.add(model);
        }
        PageImpl<BillProfileModel> pageResponse = new PageImpl<>(response, pageable, lsObject.getTotalElements());
        return pageResponse;
    }

}
