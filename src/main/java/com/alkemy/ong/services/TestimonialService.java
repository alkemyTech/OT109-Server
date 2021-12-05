package com.alkemy.ong.services;

import java.util.Optional;

import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.pojos.input.TestimonialDTO;
import com.alkemy.ong.repositories.TestimonialRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestimonialService {

    @Autowired
    TestimonialRepository testimonialRepository;

    @Autowired
    ModelMapper modelMapper;

    public TestimonialEntity create(TestimonialDTO testimonialDTO) {
        if (testimonialDTO.getName() == null || testimonialDTO.getName() == "") {
            throw new BadRequestException("Name may not be empty");
        }
        if (testimonialDTO.getContent() == null || testimonialDTO.getContent() == "") {
            throw new BadRequestException("Content may not be empty");
        }
        TestimonialEntity testimonialEntity = modelMapper.map(testimonialDTO, TestimonialEntity.class);
        return testimonialRepository.save(testimonialEntity);
    }

    public TestimonialEntity update(Long id, TestimonialDTO testimonialDTO) {

        if (!testimonialRepository.findById(id).isPresent()) {
            throw new ParamNotFound("Error: invalid testimonial id");
        }
        TestimonialEntity testimonialEntity = testimonialRepository.getById(id);
        modelMapper.map(testimonialDTO, testimonialEntity);

        return testimonialRepository.save(testimonialEntity);

    }

    public void delete(Long id) {

        Optional<TestimonialEntity> entity = testimonialRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Error: Invalid category id");
        }
        testimonialRepository.deleteById(id);

    }

    public TestimonialEntity findById(Long id) {
        return testimonialRepository.getById(id);
    }
}