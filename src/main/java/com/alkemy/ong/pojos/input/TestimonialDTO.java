package com.alkemy.ong.pojos.input;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialDTO {

    @NotEmpty(message = "this field can not be blank")
    private String name;

    private String image;
    @NotEmpty(message = "this field can not be blank")
    private String content;

}
