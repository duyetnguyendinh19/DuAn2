package com.vn.service.imple;

import com.vn.jpa.Category;
import com.vn.model.CategoryModel;
import com.vn.repository.CategoryRepo;
import com.vn.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "categoryService")
@Transactional
public class CategoryImpl implements CategoryService {

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
	public Page<CategoryModel> getList(Pageable pageable) {
		Page<Object[]> objPage = categoryRepo.getList(pageable);
		List<CategoryModel> listResponse = new ArrayList<>();
		for(Object[] obj : objPage) {
			CategoryModel categoryModel = new CategoryModel((Long) obj[0], (String) obj[1], (String) obj[2]);
			listResponse.add(categoryModel);
		}
		PageImpl<CategoryModel> pageRespone = new PageImpl<>(listResponse, pageable, objPage.getTotalElements());
		return pageRespone;
	}

}
