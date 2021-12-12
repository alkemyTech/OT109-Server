package com.alkemy.ong.dtos.responses;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CommentDTO {
    @NotBlank
    @Min(value = 1, message = "Comment id cannot be less than one.")
    private Long id;
    @NotBlank
    @Min(value = 1, message = "News id cannot be less than one.")
    private Long news_id;
    @NotBlank
    @Min(value = 1, message = "News id cannot be less than one.")
    private Long user_id;
    @NotBlank
    private String body;
}
