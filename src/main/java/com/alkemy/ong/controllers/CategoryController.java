package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.CategoryListRequestDTO;
import com.alkemy.ong.dtos.requests.CategoryPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.CategoryDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.services.CategoryService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("categories")

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryPostPutRequestDTO category) {
        CategoryDTO postCreated = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCreated);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryListRequestDTO> findAll(@NotNull @PageableDefault(value = 10) final Pageable pageable) {
        Page<Category> page = categoryService.findAllPageable(pageable);
        List<CategoryListRequestDTO> categories = new ArrayList();
        for(Category c : page){
            CategoryListRequestDTO item = new CategoryListRequestDTO();
            BeanUtils.copyProperties(c, item);
            categories.add(item);
        }
        return new PageImpl<>(categories, pageable, page.getTotalElements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO category = categoryService.findById(id);
        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryPostPutRequestDTO categoryDTO) {
        CategoryDTO result = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok().body(result);
    }
}
