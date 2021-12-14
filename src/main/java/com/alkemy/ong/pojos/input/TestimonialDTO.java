package com.alkemy.ong.pojos.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TestimonialDTO {

    @NotBlank
    @Size(min = 3, message = "Name should be at least three characters")
    private String name;
    private String image;
    private String content;

}
