package com.vn.service.imple;

import com.vn.jpa.Category;
import com.vn.jpa.Product;
import com.vn.repository.ProductRepo;
import com.vn.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service(value = "productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepo productRepo;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Product insert(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepo.delete(product);
    }

    @Override
    public Product findOne(Long id) {
        return productRepo.findOne(id);
    }

    @Override
    public List<Product> lsProductDateDesc() {
        return productRepo.lsProductDateDesc();
    }

    @Override
    public List<Product> findProductByCategoryId(Long id) {
        return productRepo.findProductByCategoryId(id);
    }
}
