package com.alkemy.ong.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alkemy.ong.dtos.responses.TestimonialListDTO;
import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.pojos.input.TestimonialDTO;
import com.alkemy.ong.repositories.TestimonialRepository;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TestimonialService {

    @Autowired
    TestimonialRepository testimonialRepository;

    @Autowired
    ModelMapper modelMapper;

    private final int GET_ALL_PAGE_SIZE = 10;

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

        if (testimonialDTO.getName() == null || testimonialDTO.getName() == "") {
            throw new BadRequestException("Name may not be empty");
        }
        if (testimonialDTO.getContent() == null || testimonialDTO.getContent() == "") {
            throw new BadRequestException("Content may not be empty");
        }
        if (!testimonialRepository.findById(id).isPresent()) {
            throw new ParamNotFound("Error: invalid testimonial id");
        }
        TestimonialEntity testimonialEntity = testimonialRepository.getById(id);
        modelMapper.map(testimonialDTO, testimonialEntity);
        if (testimonialDTO.getImage() == null || testimonialDTO.getImage() == "") {
            testimonialEntity.setImage(testimonialDTO.getImage());
        }
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

    public List<TestimonialEntity> findAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, GET_ALL_PAGE_SIZE);
        Slice<TestimonialEntity> testimonialsSlice = testimonialRepository.findAll(pageRequest);
        return testimonialsSlice.getContent();
    }

}