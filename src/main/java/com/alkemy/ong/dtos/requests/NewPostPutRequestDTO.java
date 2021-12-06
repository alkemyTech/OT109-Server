package com.alkemy.ong.dtos.requests;

import com.alkemy.ong.entities.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPostPutRequestDTO {
    private String name;

    private String content;

    private String image;

    private Long categoryId;
}
