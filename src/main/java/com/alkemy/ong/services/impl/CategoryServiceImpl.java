package com.alkemy.ong.services.impl;

import com.alkemy.ong.dtos.CategoryDTO;
import com.alkemy.ong.dtos.CategoryRequest;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.CategoryServiceException;
import com.alkemy.ong.repositories.CategoryRepository;
import com.alkemy.ong.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category entity = modelMapper.map(categoryDTO, Category.class);
        Category entityCreated = categoryRepository.save(entity);
        CategoryDTO result = modelMapper.map(entityCreated, CategoryDTO.class);
        return result;
    }

    public Category update(Category category) {
        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    public Category delete(Long id) {
        Category categoryToSoftDelete = this.findById(id);
        categoryToSoftDelete.setDeletedAt(new Date());
        return categoryRepository.save(categoryToSoftDelete);
    }

    public Category findById(Long id) throws CategoryServiceException {
        return categoryRepository.findById(id).orElseThrow(() -> (new CategoryServiceException("Category with id: " + id + " not found.")));
    }

    public Category findByName(String name) throws CategoryServiceException {
        return categoryRepository.findByName(name).orElseThrow(() -> (new CategoryServiceException("Category with name: " + name + " not found.")));
    }

    public List<CategoryRequest> findAll() throws CategoryServiceException {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryRequest> result = new ArrayList<CategoryRequest>();
        for (Category category : categories) {
            CategoryRequest categoryRequest = modelMapper.map(category, CategoryRequest.class);
            result.add(categoryRequest);
        }
        return result;
    }

}

