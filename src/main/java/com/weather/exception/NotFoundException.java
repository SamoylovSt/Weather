package com.weather.exception;

public class NotFoundException extends AppException {
    public NotFoundException(String resource, String identifier) {
        super("NOT_FOUND", resource + "not found: " + identifier);
    }
}
