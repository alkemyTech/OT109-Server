package com.alkemy.ong.dtos.responses.exceptionResponses;

import lombok.Data;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@Data
public class ApiResponseErrorsList {
    private Date timestamp;
    private String message;
    private int status;
    private String path;
    private String user;
    private List<String> errors;

    public ApiResponseErrorsList(int status, WebRequest request, List<String> errors) {
        this.timestamp = new Date();
        this.message = "Method argument not valid";
        this.status = status;
        this.path = request.getDescription(true).substring(request.getDescription(true).indexOf("/"), request.getDescription(true).indexOf(";"));
        this.user = request.getRemoteUser();
        this.errors = errors;
    }

}
