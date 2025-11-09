package com.carrentalsimple.carrentportal.exception;

public class CarNotFound extends RuntimeException {
    public CarNotFound(String message) {
        super(message);
    }
}
