package com.alkemy.ong.dtos.requests.createAndUpdate;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
/**
 * SE usa en put
 */
public class CommentPutRequestDTO {
    @NotBlank
    private String body;
}
