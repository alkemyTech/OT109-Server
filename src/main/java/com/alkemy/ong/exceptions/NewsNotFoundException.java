package com.alkemy.ong.exceptions;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException(String message){
        super(message);
    }

}
