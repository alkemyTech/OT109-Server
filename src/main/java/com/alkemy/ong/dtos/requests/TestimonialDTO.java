package com.alkemy.ong.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialDTO {

    @NotBlank
    @Size(min = 3, message = "Name should be at least three characters")
    private String name;
    private String image;
    private String content;

}
