package com.alkemy.ong.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContactPostDto {

    private String name;
    private String phone;
    private String email;
    private String message;
}
