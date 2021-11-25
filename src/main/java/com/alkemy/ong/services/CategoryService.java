package com.alkemy.ong.services;

import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.CategoryServiceException;
import com.alkemy.ong.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category create(Category category) {
        category.setCreatedAt(new Date());
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
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

    public List<Category> findAll() {
        return categoryRepository.findByDeletedAtIsNull();
    }

}

