package com.alkemy.ong.dtos.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CommentPostRequestDTO {

    @NotNull
    @Min(value = 1, message = "News id cannot be less than one.")
    private Long news_id;
    @NotNull
    @Min(value = 1, message = "News id cannot be less than one.")
    private Long user_id;
    @NotBlank
    private String body;
}
