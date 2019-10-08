package com.vn.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vn.jpa.Category;
import com.vn.model.CategoryModel;

public interface CategoryService {
	Page<Category> findAll(Pageable pageable);

	List<Category> lsCatgory();

	Category insert(Category category);

	Category update(Category category);

	void delete(Long id);

	Category findOne(Long id);

}
