package com.alkemy.ong.services;

import com.alkemy.ong.dtos.requests.CategoryListRequestDTO;
import com.alkemy.ong.dtos.requests.CategoryPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.CategoryDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.CategoryServiceException;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    public CategoryDTO create(CategoryPostPutRequestDTO category);

    public CategoryDTO update(Long id, CategoryPostPutRequestDTO category);

    public void delete(Long id);

    public CategoryDTO findById(Long id);

    public Category findByName(String name) throws CategoryServiceException;

    public List<CategoryListRequestDTO> findAll();
    
    public Page<Category> findAllPageable(Pageable pageable);
}

