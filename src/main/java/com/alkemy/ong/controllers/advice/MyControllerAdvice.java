package com.alkemy.ong.controllers.advice;

import com.alkemy.ong.exceptions.BadRequestException;
import com.alkemy.ong.exceptions.NotFoundException;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.exceptions.UserServiceException;
import com.alkemy.ong.pojos.output.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class MyControllerAdvice {

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
    //Cuando los valores son null o vacios isBlank()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse handleBadRequestException(BadRequestException badRequestException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, badRequestException.getMessage());
    }

    //Deberia romper cuando ocurre un error en la base de datos, pero solo ocurre cuando una imagen es null en MemberController
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResponse handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException, WebRequest request){
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), request, "Image cant be null" );
    }



    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        String response = "";
        for (ObjectError error : ex.getAllErrors()) {
            response += error.getDefaultMessage() + "\n";
        }
        return response;
    }

    @ExceptionHandler(ParamNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String paramNotFoundExceptionHandler(ParamNotFound ex) {
        return ex.getMessage();
    }
*/
}

