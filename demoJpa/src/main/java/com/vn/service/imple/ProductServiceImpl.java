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
import java.util.Date;
import java.util.List;

@Service(value = "productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductRepo productRepo;

    @Override
    public Page<Product> findAllByIsdelete(String isdelete,Pageable pageable) {
        return productRepo.findAllByIsdelete(isdelete,pageable);
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
    public List<Product> findProductByCategoryIdAndIsdelete(Long id, String isdelete) {
        return productRepo.findProductByCategoryIdAndIsdelete(id, isdelete);
    }

    @Override
    public Page<Product> findAllByCategoryIdAndIsdelete(Long id, Pageable pageable,String isdelete) {
        return productRepo.findAllByCategoryIdAndIsdelete(id, pageable, isdelete);
    }

    @Override
    public Page<Product> findAllProduct(Date fromDate, Date toDate, String name, String isDelete, Pageable pageable) {
        return productRepo.findAllProduct(fromDate, toDate, name, isDelete, pageable);
    }

    @Override
    public Product findAllByNameAndIsdelete(String name, String isdelete) {
        return productRepo.findAllByNameAndIsdelete(name, isdelete);
    }

    @Override
    public Page<Product> findByCategoryParentAndIsdelete(List<Long> categoryId, String isdelete, Pageable pageable) {
        return productRepo.findAllByCategoryParentAndIsdelete(categoryId,isdelete,pageable);
    }

    @Override
    public Page<Product> findAllByNameIsLikeAndIsdelete(String name, String isdelete, Pageable pageable) {
        return productRepo.findAllByNameIsLikeAndIsdelete(name, isdelete, pageable);
    }
}
