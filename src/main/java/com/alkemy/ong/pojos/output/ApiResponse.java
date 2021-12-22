package com.alkemy.ong.pojos.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private String nameExceptionClass;
    //private int status;
}
