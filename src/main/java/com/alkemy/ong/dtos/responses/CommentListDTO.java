package com.alkemy.ong.dtos.responses;

import lombok.Data;

@Data
public class CommentListDTO {
    private Long id;
    private String body;

    public CommentListDTO(Long id, String body) {
        this.id = id;
        this.body = body;
    }
}
