package com.alkemy.ong.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLoginDTO {
    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;

}
