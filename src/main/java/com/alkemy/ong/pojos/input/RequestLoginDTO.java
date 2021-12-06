package com.alkemy.ong.pojos.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLoginDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;


}
