package com.alkemy.ong.pojos.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLoginDTO {

    private String username;
    private String password;
    private String role;

}
