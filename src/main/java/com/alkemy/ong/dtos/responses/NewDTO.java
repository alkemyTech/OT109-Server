package com.alkemy.ong.dtos.responses;

import com.alkemy.ong.entities.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDTO {
    private Long id;

    private String name;

    private String content;

    private String image;

    private Category category;
}
