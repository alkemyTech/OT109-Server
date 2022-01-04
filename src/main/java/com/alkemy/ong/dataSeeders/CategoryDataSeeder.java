package com.alkemy.ong.dataSeeders;

import com.alkemy.ong.dtos.requests.CategoryPostPutRequestDTO;
import com.alkemy.ong.repositories.CategoryRepository;
import com.alkemy.ong.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryDataSeeder implements CommandLineRunner {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        CategoryPostPutRequestDTO category01 = new CategoryPostPutRequestDTO();
        category01.setName("Escuelas");
        category01.setDescription("Publicaciones relacionadas con escuelas");
        category01.setImage("https://wwww.somosmas.org/imagen_escuela.jpg");
        if (!categoryRepository.existsByName(category01.getName())) {
            categoryService.create(category01);
        }

        CategoryPostPutRequestDTO category02 = new CategoryPostPutRequestDTO();
        category02.setName("Niños");
        category02.setDescription("Publicaciones relacionadas con niños");
        category02.setImage("https://wwww.somosmas.org/imagen_niños.jpg");
        if (!categoryRepository.existsByName(category02.getName())) {
            categoryService.create(category02);
        }

        CategoryPostPutRequestDTO category03 = new CategoryPostPutRequestDTO();
        category03.setName("Juguetes");
        category03.setDescription("Publicaciones relacionadas con Juguetes");
        category03.setImage("https://wwww.somosmas.org/imagen_juguetes.jpg");
        if (!categoryRepository.existsByName(category03.getName())) {
            categoryService.create(category03);
        }

        CategoryPostPutRequestDTO category04 = new CategoryPostPutRequestDTO();
        category04.setName("Barrios");
        category04.setDescription("Publicaciones relacionadas con Barrios");
        category04.setImage("https://wwww.somosmas.org/imagen_barrios.jpg");
        if (!categoryRepository.existsByName(category04.getName())) {
            categoryService.create(category04);
        }

        CategoryPostPutRequestDTO category05 = new CategoryPostPutRequestDTO();
        category05.setName("Eventos");
        category05.setDescription("Publicaciones relacionadas con Eventos");
        category05.setImage("https://wwww.somosmas.org/imagen_eventos.jpg");
        if (!categoryRepository.existsByName(category05.getName())) {
            categoryService.create(category05);
        }

        CategoryPostPutRequestDTO category06 = new CategoryPostPutRequestDTO();
        category06.setName("Viajes");
        category06.setDescription("Publicaciones relacionadas con Viajes");
        category06.setImage("https://wwww.somosmas.org/imagen_viajes.jpg");
        if (!categoryRepository.existsByName(category06.getName())) {
            categoryService.create(category06);
        }

        CategoryPostPutRequestDTO category07 = new CategoryPostPutRequestDTO();
        category07.setName("Clases");
        category07.setDescription("Publicaciones relacionadas con Clases");
        category07.setImage("https://wwww.somosmas.org/imagen_clases.jpg");
        if (!categoryRepository.existsByName(category07.getName())) {
            categoryService.create(category07);
        }

        CategoryPostPutRequestDTO category08 = new CategoryPostPutRequestDTO();
        category08.setName("Cursos");
        category08.setDescription("Publicaciones relacionadas con Cursos");
        category08.setImage("https://wwww.somosmas.org/imagen_cursos.jpg");
        if (!categoryRepository.existsByName(category08.getName())) {
            categoryService.create(category08);
        }

        CategoryPostPutRequestDTO category09 = new CategoryPostPutRequestDTO();
        category09.setName("Voluntarios");
        category09.setDescription("Publicaciones relacionadas con Voluntarios");
        category09.setImage("https://wwww.somosmas.org/imagen_voluntarios.jpg");
        if (!categoryRepository.existsByName(category09.getName())) {
            categoryService.create(category09);
        }

        CategoryPostPutRequestDTO category10 = new CategoryPostPutRequestDTO();
        category10.setName("Donaciones");
        category10.setDescription("Publicaciones relacionadas con Donaciones");
        category10.setImage("https://wwww.somosmas.org/imagen_donaciones.jpg");
        if (!categoryRepository.existsByName(category10.getName())) {
            categoryService.create(category10);
        }

        CategoryPostPutRequestDTO category11 = new CategoryPostPutRequestDTO();
        category11.setName("Familias");
        category11.setDescription("Publicaciones relacionadas con Familias");
        category11.setImage("https://wwww.somosmas.org/imagen_familias.jpg");
        if (!categoryRepository.existsByName(category11.getName())) {
            categoryService.create(category11);
        }

        CategoryPostPutRequestDTO category12 = new CategoryPostPutRequestDTO();
        category12.setName("Celebraciones");
        category12.setDescription("Publicaciones relacionadas con Celebraciones");
        category12.setImage("https://wwww.somosmas.org/imagen_celebracion.jpg");
        if (!categoryRepository.existsByName(category12.getName())) {
            categoryService.create(category12);
        }

        CategoryPostPutRequestDTO category13 = new CategoryPostPutRequestDTO();
        category13.setName("Madres");
        category13.setDescription("Publicaciones relacionadas con Madres");
        category13.setImage("https://wwww.somosmas.org/imagen_madre.jpg");
        if (!categoryRepository.existsByName(category13.getName())) {
            categoryService.create(category13);
        }

        CategoryPostPutRequestDTO category14 = new CategoryPostPutRequestDTO();
        category14.setName("Mascotas");
        category14.setDescription("Publicaciones relacionadas con Mascotas");
        category14.setImage("https://wwww.somosmas.org/imagen_mascota.jpg");
        if (!categoryRepository.existsByName(category14.getName())) {
            categoryService.create(category14);
        }

        CategoryPostPutRequestDTO category15 = new CategoryPostPutRequestDTO();
        category15.setName("Regalos");
        category15.setDescription("Publicaciones relacionadas con Regalos");
        category15.setImage("https://wwww.somosmas.org/imagen_regalo.jpg");
        if (!categoryRepository.existsByName(category15.getName())) {
            categoryService.create(category15);
        }
    }

}
