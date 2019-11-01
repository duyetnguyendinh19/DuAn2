package com.vn.repository;

import com.vn.jpa.Category;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

@Repository(value = "categoryRepo")
public interface CategoryRepo extends JpaRepository<Category, Long> {

	Page<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive, Pageable pageable);

	List<Category> findAllByIsDeleteAndIsActive(String isDelete, String isActive);

	List<Category> findByParentIdAndIsActiveAndIsDelete(Long id, String isActive, String isDelete);

	@Query(value = "SELECT c FROM Category c WHERE "
			+ " (:name IS NULL OR :name = '' OR c.name LIKE CONCAT('%', :name, '%'))" + " AND (c.isDelete = :isDelete)"
			+ " AND (c.isActive = :isActive)")
	Page<Category> findAllCatePage(@Param("name") String name, @Param("isDelete") String delete,
			@Param("isActive") String active, Pageable pageable);

	@Query(value = "SELECT c FROM Category c WHERE c.id != :id "
			+ " AND (:name IS NULL OR :name = '' OR c.name LIKE CONCAT('%', :name, '%'))"
			+ " AND (c.isDelete = :isDelete)" + " AND (c.isActive = :isActive)" + " AND (c.parent is null) ")
	List<Category> findAllCateList(@Param("id") long id, @Param("name") String name, @Param("isDelete") String delete,
			@Param("isActive") String active);

	@Query(value = "SELECT id FROM Category  WHERE (parent_id = :parentId) AND (isactive = :active) AND (isdelete = :isdelete)", nativeQuery = true)
	List<BigInteger> listCateIdByParentId(@Param("parentId") Long parentId, @Param("active") String active, @Param("isdelete") String delete);
}
