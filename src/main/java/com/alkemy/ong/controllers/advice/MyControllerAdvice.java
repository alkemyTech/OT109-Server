package com.alkemy.ong.controllers.advice;

import com.alkemy.ong.dtos.responses.exceptionResponses.ApiResponseErrorsList;
import com.alkemy.ong.exceptions.*;
import com.alkemy.ong.dtos.responses.exceptionResponses.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

    //Para id que no existen
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse handleBadCredentials(NotFoundException notFoundException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(),request,notFoundException.getMessage());
    }
    //Para valores null o id no existentes
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamNotFound.class)
    public ApiResponse handleParamNotFound(ParamNotFound paramNotFound, WebRequest request) {
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), request, paramNotFound.getMessage());
    }
    //Usuario no encontrado// Cuando eliminamos o le hacemos un update a un usuario
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserServiceException.class)
    public ApiResponse UserServiceExceptionHandler(UserServiceException userServiceException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, userServiceException.getMessage());
    }
    //Cuando los valores son null o vacios isBlank() //No deberia ocurrir nunca ya que con handleMethodArgumentNotValid lo cubrimos
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse handleBadRequestException(BadRequestException badRequestException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, badRequestException.getMessage());
    }
    //Ocurre cuando hay errores con la base de datos
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResponse handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, "no deberia pasar nunca! ya que deberia estar validado en el controller antes de entrar a la BD" );
    }
    //Cuando un email o un nombre(atributo unico de un objeto) ya esxistes en la base de datos
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataAlreadyExistException.class)
    public ApiResponse handleDataAlreadyExistException(DataAlreadyExistException dataAlreadyExistException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, dataAlreadyExistException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public ApiResponse handleInvalidParameterException(InvalidParameterException invalidParameterException, WebRequest request) {
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, invalidParameterException.getMessage());
    }
    /*
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NewsNotFoundException.class)
    public ApiResponse handleNewsNotFoundException(NewsNotFoundException newsNotFoundException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, newsNotFoundException.getMessage());
    }*/
    //Cubre la validacion de un objeto request
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex,new ApiResponseErrorsList(HttpStatus.BAD_REQUEST.value(), request, errors), headers, HttpStatus.BAD_REQUEST, request);
    }

}

