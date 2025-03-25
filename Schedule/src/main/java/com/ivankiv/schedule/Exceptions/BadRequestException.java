package com.ivankiv.schedule.Exceptions;

public class BadRequestException extends Exception {
    public BadRequestException() {
    }

    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
