package com.alkemy.ong.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryPostRequestDTO {

    private String name;
    private String description;
    private String image;
}
