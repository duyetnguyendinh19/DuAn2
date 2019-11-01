package com.vn.service.imple;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.jpa.Category;
import com.vn.repository.CategoryRepo;
import com.vn.service.CategoryService;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryRepo categoryRepo;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    @Override
    public List<Category> lsCatgory() {
        return categoryRepo.findAll();
    }

    @Override
    public Category insert(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepo.delete(id);
    }

    @Override
    public Category findOne(Long id) {
        return categoryRepo.findOne(id);
    }

    @Override
    public Page<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive, Pageable pageable) {
        return categoryRepo.findAllByIsDeleteAndIsActive(isDelete, isActive, pageable);
    }

    @Override
    public List<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive) {
        return categoryRepo.findAllByIsDeleteAndIsActive(isDelete, isActive);
    }

    @Override
    public List<Category> findByParentIdAndIsActiveAndIsDelete(Long id, String isActive, String isDelete) {
        return categoryRepo.findByParentIdAndIsActiveAndIsDelete(id, isActive, isDelete);
    }

    @Override
    public List<BigInteger> getListCategoryById(Long parentId, String active, String delete) {
        return categoryRepo.listCateIdByParentId(parentId, active, delete);
    }

    @Override
    public Page<Category> findAllCatePage(String name, String delete, String active, Pageable pageable) {
        return categoryRepo.findAllCatePage(name, delete, active, pageable);
    }

    @Override
    public List<Category> findAllCateList(long id, String name, String delete, String active) {
        return categoryRepo.findAllCateList(id, name, delete, active);
    }

}
