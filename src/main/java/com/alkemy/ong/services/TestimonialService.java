package com.alkemy.ong.services;

import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repositories.TestimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestimonialService {

    @Autowired
    TestimonialRepository testimonialRepository;

    public void create(TestimonialEntity entity) {
        testimonialRepository.save(entity);
    }

    public void update(Long id, TestimonialEntity testimonialEntity) {
        Optional<TestimonialEntity> entity = testimonialRepository.findById(id);
         if (!entity.isPresent()) {
            throw new ParamNotFound("Error: invalid testimonial id");
        } 
        entity.get().setName(testimonialEntity.getName());
        entity.get().setImage(testimonialEntity.getImage());
        entity.get().setContent(testimonialEntity.getContent());
        testimonialRepository.save(entity.get());
    }

    public void delete(Long id) {
        testimonialRepository.deleteById(id);
    }

    public TestimonialEntity findById(Long id) {
        return testimonialRepository.getById(id);
    }
}