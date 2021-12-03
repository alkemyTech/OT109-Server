package com.alkemy.ong.services.impl;

import com.alkemy.ong.dtos.requests.CategoryListRequestDTO;
import com.alkemy.ong.dtos.requests.CategoryPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.CategoryDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.CategoryServiceException;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repositories.CategoryRepository;
import com.alkemy.ong.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO create(CategoryPostPutRequestDTO categoryDTO) {
        if (categoryDTO.getName() == null) {
            throw new BadRequestException("Name may not be empty");
        }
        Category entity = modelMapper.map(categoryDTO, Category.class);
        Category entityCreated = categoryRepository.save(entity);
        CategoryDTO result = modelMapper.map(entityCreated, CategoryDTO.class);
        return result;
    }

    public CategoryDTO update(Long id, CategoryPostPutRequestDTO category) {
        if (category.getName() == null) {
            throw new BadRequestException("Name may not be empty");
        }
        Optional<Category> entity = categoryRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Error: Invalid category id");
        }
        entity.get().setName(category.getName());
        entity.get().setDescription(category.getDescription());
        entity.get().setImage(category.getImage());
        Category entitySaved = categoryRepository.save(entity.get());
        CategoryDTO result = modelMapper.map(entitySaved, CategoryDTO.class);
        return result;
    }

    public void delete(Long id) {
        Optional<Category> entity = categoryRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Error: Invalid category id");
        }
        categoryRepository.deleteById(id);
    }

    public CategoryDTO findById(Long id) {
        Optional<Category> entity = categoryRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Error: Invalid category id");
        }
        CategoryDTO result = modelMapper.map(entity.get(), CategoryDTO.class);
        return result;
    }

    public Category findByName(String name) throws CategoryServiceException {
        return categoryRepository.findByName(name).orElseThrow(() -> (new CategoryServiceException("Category with name: " + name + " not found.")));
    }

    public List<CategoryListRequestDTO> findAll() throws CategoryServiceException {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryListRequestDTO> result = new ArrayList<CategoryListRequestDTO>();
        for (Category category : categories) {
            CategoryListRequestDTO categoryListRequestDTO = modelMapper.map(category, CategoryListRequestDTO.class);
            result.add(categoryListRequestDTO);
        }
        return result;
    }

}

