package com.alkemy.ong.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ActivityPostPutRequestDTO {
    @NotNull(message = "Name may not be empty")
    private String name;
    @NotNull(message = "Content may not be empty")
    private String content;
    @NotNull(message = "Image may not be empty")
    private String image;
}
