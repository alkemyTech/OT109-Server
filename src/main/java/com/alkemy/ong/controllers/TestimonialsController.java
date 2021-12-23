package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.TestimonialListDTO;
import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.dtos.requests.TestimonialDTO;
import com.alkemy.ong.dtos.responses.PageDTO;
import com.alkemy.ong.services.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/testimonials")
public class TestimonialsController {

    @Autowired
    private TestimonialService testimonialService;

    @Autowired
    private ModelMapper modelMapper;

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

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public PageDTO<TestimonialListDTO> findAll(@RequestParam(defaultValue = "0")  @Min(value = 0, message = "Page must be 0 or greater.") int page,
                                    @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size cannot be less than one.") int size
    ) {
        Slice<TestimonialEntity> testimonialsSlice = testimonialService.findAll(page, size);
        
        return toPageDTO(testimonialsSlice);
    }

    public TestimonialListDTO convertToListDTO(TestimonialEntity te){
        TestimonialListDTO teDTO = modelMapper.map(te, TestimonialListDTO.class);
        return teDTO;
    }

    public PageDTO<TestimonialListDTO> toPageDTO(Slice<TestimonialEntity> slice){
        List<TestimonialListDTO> testimonialsDTOS = slice.stream()
                .map(this::convertToListDTO)
                .collect(Collectors.toList());
        
        String url = "http://localhost:9800/testimonials?page=";
        Page<TestimonialListDTO> outputPage = new PageImpl<>(testimonialsDTOS, PageRequest.of(slice.getNumber(), slice.getSize()), slice.getNumberOfElements());
        return new PageDTO<>(outputPage, url);
    }


}
