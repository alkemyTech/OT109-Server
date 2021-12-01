package com.alkemy.ong.services;

import com.alkemy.ong.dtos.CategoryDTO;
import com.alkemy.ong.dtos.CategoryRequest;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.CategoryServiceException;

import java.util.List;

public interface CategoryService {

    public CategoryDTO create(CategoryDTO category);

    public Category update(Category category);

    public Category delete(Long id);

    public Category findById(Long id);

    public Category findByName(String name) throws CategoryServiceException;

    public List<CategoryRequest> findAll();
}

