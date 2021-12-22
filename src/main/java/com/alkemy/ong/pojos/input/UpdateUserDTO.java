package com.alkemy.ong.pojos.input;

import lombok.*;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    
    private String firstName;
    
    private String lastName;
    
    @Email
    private String email;
    
    private String password;

    @Pattern(regexp = "((http|https)://)?(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)(.jpg|.png|.jpeg)", message="The image URL has invalid format")
    private String photo;

    private String role;
    
    
}
