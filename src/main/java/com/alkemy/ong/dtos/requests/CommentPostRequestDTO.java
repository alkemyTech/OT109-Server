package com.alkemy.ong.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostRequestDTO {

    private Long newId;
    private Long userId;
    private String body;
}
