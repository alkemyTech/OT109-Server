package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.CategoryListRequestDTO;
import com.alkemy.ong.dtos.requests.CategoryPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.CategoryDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.pojos.output.PageDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryPostPutRequestDTO category) {
        CategoryDTO postCreated = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCreated);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<CategoryListRequestDTO> findAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNumber, @RequestParam(value = "size",required = false, defaultValue = "10") Integer size) {
        PageRequest pageable = PageRequest.of(pageNumber, size);
        Page<Category> page = categoryService.findAllPageable(pageable);
        if(page.getNumberOfElements() == 0){
            throw new ParamNotFound("Page not found");
        }
        return preparePageDTO(page, pageable);
        
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
    
    private PageDTO<CategoryListRequestDTO> preparePageDTO(Page<Category> page, Pageable pageable){
        final String url = "localhost:9800/categories?page=";
        List<CategoryListRequestDTO> categories = new ArrayList();
        for(Category c : page){
            CategoryListRequestDTO item = new CategoryListRequestDTO();
            BeanUtils.copyProperties(c, item);
            categories.add(item);
        }
        Page<CategoryListRequestDTO> outputPage = new PageImpl<>(categories, pageable, page.getTotalElements());
        return new PageDTO<>(outputPage, url);
    }
    
    @ExceptionHandler(ParamNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String paramNotFoundExceptionHandler(ParamNotFound ex) {
        return ex.getMessage();
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String globalExceptionHandler(Exception ex) {
        return ex.getMessage();
    }
}
