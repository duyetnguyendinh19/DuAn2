package com.vn.service.imple;

import com.vn.jpa.Reject;
import com.vn.model.ChartDashboardBillOrder;
import com.vn.repository.RejectRepo;
import com.vn.service.RejectService;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "rejectService")
@Transactional
public class RejectServiceImpl implements RejectService {

    @Resource
    private RejectRepo rejectRepo;

    @Override
    public Page<Reject> findAll(Pageable pageable) {
        return rejectRepo.findAll(pageable);
    }

    @Override
    public Reject insert(Reject reject) {
        return rejectRepo.save(reject);
    }

    @Override
    public Reject update(Reject reject) {
        return rejectRepo.save(reject);
    }

    @Override
    public void delete(Reject reject) {
        rejectRepo.delete(reject);
    }

    @Override
    public Reject findOne(Long id) {
        return rejectRepo.findOne(id);
    }

    @Override
    public List<ChartDashboardBillOrder> listCountRejectDashBoard(Date date) {
        List<ChartDashboardBillOrder> response = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateTime time = new DateTime(date);
        Date fromDate = time.plusDays(-7).toDate();
        Date toDate = time.withTimeAtStartOfDay().toDate();
        List<Object[]> lsObject = rejectRepo.listCountRejectDashBoard(fromDate, toDate);
        for (Object[] each : lsObject) {
            String key = sdf.format(each[0]);
            BigInteger bigInteger = (BigInteger) each[1];
            Integer value = Integer.parseInt(String.valueOf(bigInteger));
            ChartDashboardBillOrder order = new ChartDashboardBillOrder(key, value);
            response.add(order);
        }
        return response;
    }

    @Override
    public Page<Reject> pageByDateAndCode(Date fromDate, Date toDate, String code, Pageable pageable) {
        return rejectRepo.pageByDateAndCode(fromDate, toDate, code, pageable);
    }


}
