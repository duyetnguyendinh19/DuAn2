package com.vn.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vn.jpa.Category;

public interface CategoryService {
	Page<Category> findAll(Pageable pageable);

	List<Category> lsCatgory();

	Category insert(Category category);

	Category update(Category category);

	void delete(Long id);

	Category findOne(Long id);

	Page<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive, Pageable pageable);

	List<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive);

	Page<Category> findAllCatePage(String name, String delete, String active, Pageable pageable);

	List<Category> findAllCateList(long id, String name, String delete, String active);

	List<Category> findByParentIdAndIsActiveAndIsDelete(Long id, String isActive, String isDelete);

	List<BigInteger> getListCategoryById(Long parentId, String active, String delete);
}
