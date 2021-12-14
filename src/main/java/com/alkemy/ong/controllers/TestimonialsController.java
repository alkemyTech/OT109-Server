package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.TestimonialListDTO;
import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.pojos.input.TestimonialDTO;
import com.alkemy.ong.services.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0")  @Min(value = 0, message = "Page must be 0 or greater") int page) {

        List<TestimonialEntity> testimonials = testimonialService.findAll(page);
        List<TestimonialListDTO> response = testimonials.stream()
                .map(this::convertToListDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public TestimonialListDTO convertToListDTO(TestimonialEntity te){
        TestimonialListDTO teDTO = modelMapper.map(te, TestimonialListDTO.class);
        return teDTO;
    }
}
