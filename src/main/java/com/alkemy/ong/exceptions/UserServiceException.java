package com.alkemy.ong.exceptions;

public class UserServiceException extends RuntimeException {

    public UserServiceException() {
    }

    public UserServiceException(String msg) {
        super(msg);
    }
}
