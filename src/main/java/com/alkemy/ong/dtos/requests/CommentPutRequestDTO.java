package com.alkemy.ong.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentPutRequestDTO {
    @NotBlank
    private String body;
}
