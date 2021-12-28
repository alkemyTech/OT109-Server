package com.alkemy.ong.dtos.responses;

import com.alkemy.ong.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRegisterDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private Role role;
    private String token;

}
