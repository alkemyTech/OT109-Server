package com.alkemy.ong.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityPostPutRequestDTO {
    @NotNull(message = "Name may not be empty")
    private String name;
    @NotNull(message = "Content may not be empty")
    private String content;
    @NotNull(message = "Image may not be empty")
    private String image;
}
