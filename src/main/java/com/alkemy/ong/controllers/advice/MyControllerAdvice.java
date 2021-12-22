package com.alkemy.ong.controllers.advice;

import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.exceptions.ParamNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException notFoundException) {
        return notFoundException.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamNotFound.class)
    public String handleParamNotFound(ParamNotFound paramNotFound) {
        return paramNotFound.getMessage();
    }

}

