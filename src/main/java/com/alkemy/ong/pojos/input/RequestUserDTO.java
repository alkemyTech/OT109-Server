package com.alkemy.ong.pojos.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserDTO {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;        
    
}
