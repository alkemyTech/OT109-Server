package com.alkemy.ong.dtos.requests.createAndUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Se  usa en create y put
 */
public class ActivityPostPutRequestDTO {
    @NotBlank
    @NotNull(message = "Name may not be empty")
    private String name;
    @NotBlank
    @NotNull(message = "Content may not be empty")
    private String content;
    @NotBlank
    @NotNull(message = "Image may not be empty")
    //Falta agregar validacion de link image
    private String image;
}
