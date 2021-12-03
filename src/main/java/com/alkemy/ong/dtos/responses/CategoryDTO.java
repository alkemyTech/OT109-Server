package com.alkemy.ong.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String image;
}
