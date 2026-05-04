package com.company.simpleservice.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Resource not found with id: " + id);
    }

}