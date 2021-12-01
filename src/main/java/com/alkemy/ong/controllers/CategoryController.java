package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.CategoryDTO;
import com.alkemy.ong.dtos.CategoryRequest;
import com.alkemy.ong.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("categories")

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> create (@RequestBody @Valid CategoryDTO category){
        CategoryDTO postCreated = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCreated);
    }

    @GetMapping
    public ResponseEntity<List<CategoryRequest>> findAll(){
        List<CategoryRequest> categories = categoryService.findAll();
        return ResponseEntity.ok().body(categories);
    }
}
