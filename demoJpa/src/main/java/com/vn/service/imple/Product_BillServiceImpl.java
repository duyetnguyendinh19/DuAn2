package com.vn.service.imple;

import com.vn.jpa.Product;
import com.vn.jpa.Product_Bill;
import com.vn.repository.Product_BillRepo;
import com.vn.service.Product_BillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
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
}
