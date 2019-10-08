package com.vn.repository;

import com.vn.jpa.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository(value = "categoryRepo")
public interface CategoryRepo extends JpaRepository<Category, Long> {

	@Query(value = "SELECT c.id, c.name,(SELECT c1.name FROM Category c1 WHERE c1.id = c.parent_Id) nameParent FROM Category c", nativeQuery = true)
	Page<Object[]> getList(Pageable pageable);
}
