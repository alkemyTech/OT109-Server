package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.CategoryPostRequestDTO;
import com.alkemy.ong.dtos.responses.CategoryDTO;
import com.alkemy.ong.dtos.requests.CategoryListRequestDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.CategoryServiceException;

import java.util.List;

public interface CategoryService {

    public CategoryDTO create(CategoryPostRequestDTO category);

    public Category update(Category category);

    //public Category delete(Long id);

    public CategoryDTO findById(Long id);

    public Category findByName(String name) throws CategoryServiceException;

    public List<CategoryListRequestDTO> findAll();
}

