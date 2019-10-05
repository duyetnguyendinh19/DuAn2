package com.vn.service;

import com.vn.jpa.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    List<Category> lsCatgory();

    Category insert(Category category);

    Category update(Category category);

    void delete(Long id);

    Category findOne(Long id);

}
