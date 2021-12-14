package com.alkemy.ong.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ParamNotFound.class})
    protected ResponseEntity<Object> handleParamNotFound(RuntimeException ex, WebRequest request) {
        ApiError errorDTO = new ApiError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                Arrays.asList("Param Not Found")
        );
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        ApiError errorDTO = new ApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                Arrays.asList("Error: a required field is missing")
        );
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handler for @Valid annotation
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Method argument not valid", errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);

    }


}
