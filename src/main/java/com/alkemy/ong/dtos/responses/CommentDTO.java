package com.alkemy.ong.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private Long id;
    private Long new_id;
    private Long user_id;
    private String body;
}
