package com.alkemy.ong.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class NewsExceptionHandler {

    @ExceptionHandler(value = NewsNotFoundException.class)
    public ResponseEntity<?> handleNewsNotFoundException(NewsNotFoundException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getContextPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
    }

}
