package com.alkemy.ong.dtos.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestimonialsPageDTO {
    private List<TestimonialListDTO> testimonialsDTOS;
    private String previousPage;
    private String nextPage;

}
