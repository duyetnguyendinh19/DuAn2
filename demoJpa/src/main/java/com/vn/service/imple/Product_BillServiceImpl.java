package com.vn.service.imple;

import com.vn.jpa.Product;
import com.vn.jpa.Product_Bill;
import com.vn.model.ChartDashboardBillOrder;
import com.vn.model.KeyValueStringIntegerModel;
import com.vn.repository.Product_BillRepo;
import com.vn.service.Product_BillService;
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

@Service(value = "productBillService")
@Transactional
public class Product_BillServiceImpl implements Product_BillService {

    @Resource
    private Product_BillRepo product_billRepo;

    @Override
    public Page<Product_Bill> findAll(Pageable pageable) {
        return product_billRepo.findAll(pageable);
    }

    @Override
    public Product_Bill insert(Product_Bill product_bill) {
        return product_billRepo.save(product_bill);
    }

    @Override
    public Product_Bill update(Product_Bill product_bill) {
        return product_billRepo.save(product_bill);
    }

    @Override
    public void delete(Product_Bill product_bill) {
        product_billRepo.delete(product_bill);
    }

    @Override
    public Product_Bill findOne(Long id) {
        return product_billRepo.findOne(id);
    }

    @Override
    public Long countQuantityByProduct(Long id) {
        return product_billRepo.countQuantityByProduct(id);
    }

    @Override
    public List<Product_Bill> findByProductId(Long id) {
        return product_billRepo.findByProductId(id);
    }

    @Override
    public List<Product_Bill> findByBill_Id(Long id) {
        return product_billRepo.findByBill_Id(id);
    }

    @Override
    public List<ChartDashboardBillOrder> listCountBillGrByDateBillId(Date date) {
        List<ChartDashboardBillOrder> response = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateTime time = new DateTime(date);
        Date fromDate = time.plusDays(-7).toDate();
        Date toDate = time.withTimeAtStartOfDay().toDate();
        List<Object[]> lsObject = product_billRepo.listCountBillGrByDateBillId(fromDate, toDate);
        for(Object[] each : lsObject){
            String key = sdf.format(each[0]);
            Long val = (Long) each[1];
            Integer value = val.intValue();
            ChartDashboardBillOrder model = new ChartDashboardBillOrder(key, value);
            response.add(model);
        }
        return response;
    }

    @Override
    public List<KeyValueStringIntegerModel> listCountBillOrGrDateNow() {
        List<KeyValueStringIntegerModel> response = new ArrayList<>();
        List<Object[]> lsObject = product_billRepo.listCountBillOrGrDateNow();
        for(Object[] each : lsObject){
            String name = (String) each[0];
            Long total = (Long) each[1];
            Integer count = total.intValue();
            KeyValueStringIntegerModel model = new KeyValueStringIntegerModel(name, count);
            response.add(model);
        }
        return response;
    }
}
