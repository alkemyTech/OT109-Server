package com.alkemy.ong.dtos.requests.createAndUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Se usa en create y put
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewPostPutRequestDTO {
    @NotBlank(message = "Name may not be empty")
    private String name;

    @NotBlank(message = "Content may not be empty")
    private String content;

    @NotBlank(message = "Image may not be empty")
    private String image;

    @NotNull(message = "Category may not be empty")
    private Long categoryId;
}
