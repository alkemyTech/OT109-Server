package com.alkemy.ong.controllers;

import com.alkemy.ong.dtos.responses.TestimonialListDTO;
import com.alkemy.ong.dtos.responses.TestimonialsPageDTO;
import com.alkemy.ong.entities.TestimonialEntity;
import com.alkemy.ong.dtos.requests.createAndUpdate.TestimonialDTO;
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
    public TestimonialEntity updateTestimonial(@PathVariable Long id,@Valid @RequestBody TestimonialDTO testimonialDTO) {

        return testimonialService.update(id, testimonialDTO);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        testimonialService.delete(id);

    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0")  @Min(value = 0, message = "Page must be 0 or greater.") int page,
                                    @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size cannot be less than one.") int size
    ) {
        Slice<TestimonialEntity> testimonialsSlice = testimonialService.findAll(page, size);
        TestimonialsPageDTO testimonialsPage = toTestimonialPageDTO(testimonialsSlice);
        return new ResponseEntity<>(testimonialsPage,HttpStatus.OK);
    }

    public TestimonialListDTO convertToListDTO(TestimonialEntity te){
        TestimonialListDTO teDTO = modelMapper.map(te, TestimonialListDTO.class);
        return teDTO;
    }

    public TestimonialsPageDTO toTestimonialPageDTO(Slice<TestimonialEntity> testimonialsSlice){
        List<TestimonialListDTO> testimonialsDTOS = testimonialsSlice.stream()
                .map(this::convertToListDTO)
                .collect(Collectors.toList());

        String url = "http://localhost:9800/testimonials?page=";
        TestimonialsPageDTO testimonialsPage = new TestimonialsPageDTO();
        testimonialsPage.setTestimonialsDTOS(testimonialsDTOS);
        if(testimonialsSlice.hasPrevious()) {
            testimonialsPage.setPreviousPage(url + testimonialsSlice.previousPageable().getPageNumber());
        }
        if(testimonialsSlice.hasNext()) {
            testimonialsPage.setNextPage(url + testimonialsSlice.nextPageable().getPageNumber());
        }
        return testimonialsPage;
    }


}
