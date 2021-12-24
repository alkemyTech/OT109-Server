package com.alkemy.ong.dtos.requests.createAndUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;


}