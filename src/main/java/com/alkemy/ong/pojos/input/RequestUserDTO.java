package com.alkemy.ong.pojos.input;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserDTO {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String password;

    private String photo;        
    
}
