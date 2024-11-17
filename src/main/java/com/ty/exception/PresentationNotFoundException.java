package com.ty.exception;

public class PresentationNotFoundException extends RuntimeException {
    public PresentationNotFoundException(String message) {
        super(message);
    }
}
