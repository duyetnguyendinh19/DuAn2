package com.vn.repository;

import com.vn.jpa.Category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "categoryRepo")
public interface CategoryRepo extends JpaRepository<Category, Long> {

	Page<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive, Pageable pageable);
	
	List<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive);

	List<Category> findByParentIdAndIsActiveAndIsDelete(Long id, String isActive, String isDelete);

}
