package com.alkemy.ong.dtos.responses.exceptionResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Date timestamp;
    private String message;
    private int status;
    private String path;
    private String user;

    public ApiResponse(int status, WebRequest request, String message) {
        this.timestamp = new Date();
        this.message = message;
        this.status = status;
        this.path = request.getDescription(true).substring(request.getDescription(true).indexOf("/"), request.getDescription(true).indexOf(";"));
        this.user = request.getRemoteUser();
    }
}
