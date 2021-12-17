package com.alkemy.ong.services.impl;

import com.alkemy.ong.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    CategoryRepository categoryRepoMock = Mockito.mock(CategoryRepository.class);

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findAll() {
    }
}