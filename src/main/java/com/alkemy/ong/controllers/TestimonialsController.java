package com.alkemy.ong.controllers;

import javax.validation.Valid;

import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.pojos.input.TestimonialDTO;
import com.alkemy.ong.services.TestimonialService;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testimonials")
public class TestimonialsController {

    @Autowired
    private TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<TestimonialEntity> addTestimonial(@Valid @RequestBody TestimonialDTO testimonialDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialService.create(testimonialDTO));

    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{id}")
    public TestimonialEntity updateTestimonial(@PathVariable Long id, @RequestBody TestimonialDTO testimonialDTO) {

        return testimonialService.update(id, testimonialDTO);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        testimonialService.delete(id);

    }

}
