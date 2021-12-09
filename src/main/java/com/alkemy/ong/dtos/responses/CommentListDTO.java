package com.alkemy.ong.dtos.responses;

import lombok.Data;

@Data
public class CommentListDTO {
    private Long id;
    private String body;
    private String email;

    public CommentListDTO(Long id, String body, String email) {
        this.id = id;
        this.body = body;
        this.email = email;
    }
}
