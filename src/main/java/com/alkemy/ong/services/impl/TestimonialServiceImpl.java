package com.alkemy.ong.services.impl;

import java.util.Optional;

import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.dtos.requests.createAndUpdate.TestimonialDTO;
import com.alkemy.ong.repositories.TestimonialRepository;
import com.alkemy.ong.services.TestimonialService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class TestimonialServiceImpl implements TestimonialService{

    @Autowired
    TestimonialRepository testimonialRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
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

    @Override
    public TestimonialEntity update(Long id, TestimonialDTO testimonialDTO) {

        if (testimonialDTO.getName() == null || testimonialDTO.getName() == "") {
            throw new BadRequestException("Name may not be empty");
        }
        if (testimonialDTO.getContent() == null || testimonialDTO.getContent() == "") {
            throw new BadRequestException("Content may not be empty");
        }
        if (!testimonialRepository.findById(id).isPresent()) {
            throw new NotFoundException("Error: invalid testimonial id");
        }
        TestimonialEntity testimonialEntity = testimonialRepository.getById(id);
        modelMapper.map(testimonialDTO, testimonialEntity);
        if (testimonialDTO.getImage() == null || testimonialDTO.getImage() == "") {
            testimonialEntity.setImage(testimonialDTO.getImage());
        }
        return testimonialRepository.save(testimonialEntity);

    }

    @Override
    public void delete(Long id) {

        Optional<TestimonialEntity> entity = testimonialRepository.findById(id);
        if (!entity.isPresent()) {
            throw new NotFoundException("Error: Invalid testimonials id");
        }
        testimonialRepository.deleteById(id);

    }

    @Override
    public TestimonialEntity findById(Long id) {
        return testimonialRepository.getById(id);
    }

    @Override
    public Slice<TestimonialEntity> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Slice<TestimonialEntity> testimonialsSlice = testimonialRepository.findBy(pageRequest);
        if(testimonialsSlice.isEmpty()){
            throw new NotFoundException("Page not found.");
        }
        else{
            return testimonialsSlice;
        }

    }

}