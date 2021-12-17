package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.requests.CategoryListRequestDTO;
import com.alkemy.ong.dtos.requests.CategoryPostPutRequestDTO;
import com.alkemy.ong.dtos.responses.CategoryDTO;
import com.alkemy.ong.entities.Category;
import com.alkemy.ong.services.CategoryService;
import com.alkemy.ong.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {


    private CategoryController categoryController;

    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;
    private Category category;
    private CategoryDTO result;
    private CategoryPostPutRequestDTO categoryRequest;
    private CategoryListRequestDTO categoryListRequestDTO;
    private List<CategoryListRequestDTO> listRequestDTOS;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
        categoryService = Mockito.mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);
        category = new Category(1L,
                                "name",
                                "description",
                                "image", Date.valueOf(LocalDate.now()),
                                null, null );
        categoryRequest = new CategoryPostPutRequestDTO();
        categoryRequest.setName("name");
        categoryRequest.setDescription("description");
        categoryRequest.setImage("image");

        result = modelMapper.map(category, CategoryDTO.class);

        categoryListRequestDTO = new CategoryListRequestDTO();
        categoryListRequestDTO.setName("name");
        categoryListRequestDTO.setId(1L);


    }

    @Test
    void create() {
        Mockito.when(categoryService.create(categoryRequest)).thenReturn(result);
        ResponseEntity respuestaCreate = categoryController.create(categoryRequest);
        System.out.println(respuestaCreate);
        assertEquals(respuestaCreate, new ResponseEntity(result, HttpStatus.CREATED));
        verify(categoryService, times(1)).create(categoryRequest);
    }

    @Test
    void findAll() {
        Mockito.when(categoryService.findAll()).thenReturn(listRequestDTOS);
        ResponseEntity respuestaList = categoryController.findAll();
        assertEquals(respuestaList, new ResponseEntity(listRequestDTOS, HttpStatus.OK));
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void findById() {
        Mockito.when(categoryService.findById(1L)).thenReturn(result);
        ResponseEntity respuestaId = categoryController.findById(1L);
        assertEquals(respuestaId, new ResponseEntity(result, HttpStatus.OK));
        verify(categoryService, times(1)).findById(1L);
    }

    @Test
    void delete() {
        ResponseEntity respuestaDelete = categoryController.delete(1L);
        assertEquals(respuestaDelete, new ResponseEntity(HttpStatus.NO_CONTENT));
        verify(categoryService, times(1)).delete(1L);
    }

    @Test
    void update() {
        Mockito.when(categoryService.update(1L, categoryRequest)).thenReturn(result);
        ResponseEntity respuestaUpd = categoryController.update(1L, categoryRequest);
        assertEquals(respuestaUpd, new ResponseEntity(result, HttpStatus.OK));
        verify(categoryService, times(1)).update(1L, categoryRequest);

    }
}