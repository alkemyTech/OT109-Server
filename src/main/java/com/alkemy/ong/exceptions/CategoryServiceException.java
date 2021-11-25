package com.alkemy.ong.exceptions;

public class CategoryServiceException extends RuntimeException {

    public CategoryServiceException() {
    }

    public CategoryServiceException(String message) {
        super(message);
    }
}
