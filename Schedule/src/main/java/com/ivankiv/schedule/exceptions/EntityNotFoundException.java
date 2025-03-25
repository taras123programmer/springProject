package com.ivankiv.schedule.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(){}

    public EntityNotFoundException(String message){
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
