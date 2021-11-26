package com.alkemy.ong.exceptions;


public class ActivityServiceException extends RuntimeException {

    public ActivityServiceException() {
    }

    public ActivityServiceException(String message) {
        super(message);
    }
}
