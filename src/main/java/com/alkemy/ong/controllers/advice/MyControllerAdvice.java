package com.alkemy.ong.controllers.advice;

import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.output.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class MyControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse handleBadCredentials(NotFoundException notFoundException){
        return new ApiResponse(notFoundException.getMessage(),notFoundException.getClass().getName());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamNotFound.class)
    public ApiResponse handleParamNotFound(ParamNotFound paramNotFound) {
        return new ApiResponse(paramNotFound.getMessage(),paramNotFound.getClass().getName());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserServiceException.class)
    public ApiResponse UserServiceExceptionHandler(UserServiceException userServiceException){
        return new ApiResponse(userServiceException.getMessage(),userServiceException.getClass().getName());
    }



/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public String NullPointerExceptionHandler(NullPointerException ex){
        return ex.getMessage();
    }
*/
}

