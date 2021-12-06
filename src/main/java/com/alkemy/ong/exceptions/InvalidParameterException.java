package com.alkemy.ong.exceptions;

public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException(String message){
        super(message);
    }
}
