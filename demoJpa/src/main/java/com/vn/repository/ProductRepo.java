package com.vn.repository;

import com.vn.jpa.Category;
import com.vn.jpa.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value = "productRepo")
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findAllByIsdelete(String delete, Pageable pageable);

    @Query(value = "SELECT * FROM Product WHERE isdelete = 'N' ORDER BY id DESC LIMIT 8 ", nativeQuery = true)
    List<Product> lsProductDateDesc();

    List<Product> findProductByCategoryIdAndIsdelete(Long id, String isdelete);

    Page<Product> findAllByCategoryIdAndIsdelete(Long id, Pageable pageable, String isdelete);

    @Query(value = "SELECT p FROM Product p WHERE (p.createDate BETWEEN :fromDate AND :toDate)" +
            "AND (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%'))" +
            "AND (p.isdelete = :isDelete)")
    Page<Product> findAllProduct(@Param("fromDate") Date fromDate,
                                 @Param("toDate") Date toDate,
                                 @Param("name") String name,
                                 @Param("isDelete") String delete,
                                 Pageable pageable);

    Product findAllByNameAndIsdelete(String name, String isdelete);

    @Query(value = "SELECT p FROM Product p WHERE p.category.id IN (:lsCategoryId) AND (p.isdelete = :isdelete)", nativeQuery = false)
    Page<Product> findAllByCategoryParentAndIsdelete(@Param("lsCategoryId") List<Long> categoryId,@Param("isdelete") String isdelete, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE (:name IS NULL OR :name = '' OR p.name LIKE CONCAT('%', :name, '%')) AND (p.isdelete = :isdelete)")
    Page<Product> findAllByNameIsLikeAndIsdelete(@Param("name") String name,@Param("isdelete") String isdelete, Pageable pageable);
}
