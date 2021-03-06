package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.CategoryListRequestDTO;
import com.alkemy.ong.dtos.requests.CategoryPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.CategoryDTO;
import com.alkemy.ong.dtos.responses.PageDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.services.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO create(@Valid @RequestBody CategoryPostPutRequestDTO category) {
        return categoryService.create(category);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @Valid @RequestBody CategoryPostPutRequestDTO categoryDTO) {
        categoryService.update(id, categoryDTO);
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

    private PageDTO<CategoryListRequestDTO> preparePageDTO(Page<Category> page, Pageable pageable){
        final String url = "localhost:9800/categories?page=";
        List<CategoryListRequestDTO> categories = new ArrayList<>();
        for(Category c : page){
            CategoryListRequestDTO item = new CategoryListRequestDTO();
            BeanUtils.copyProperties(c, item);
            categories.add(item);
        }
        Page<CategoryListRequestDTO> outputPage = new PageImpl<>(categories, pageable, page.getTotalElements());
        return new PageDTO<>(outputPage, url);
    }

}
