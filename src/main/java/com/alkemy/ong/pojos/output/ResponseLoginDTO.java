package com.alkemy.ong.pojos.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginDTO {

    private String firstName;
    private String token;
    private String email;
    private String role;

}
