package com.alkemy.ong.pojos.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TestimonialDTO {

    @NotBlank
    @Size(min = 3, message = "Name should be at least three characters")
    private String name;
    private String image;
    private String content;

}
