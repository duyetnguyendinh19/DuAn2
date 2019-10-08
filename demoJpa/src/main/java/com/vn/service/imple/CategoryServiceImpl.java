package com.vn.service.imple;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.jpa.Category;
import com.vn.model.CategoryModel;
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
	public Page<Category> findAllByIsDeleteAndIsActiveAndName(String isDelete, String isActive, Pageable pageable,
			String name) {
		return categoryRepo.findAllByIsDeleteAndIsActiveAndName(isDelete, isActive, pageable, name);
	}

}
