package com.alkemy.ong.dtos.responses;

import com.alkemy.ong.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewDTO {
    private Long id;

    private String name;

    private String content;

    private String image;

    private CategoryDTO category;
}
