package com.nhnacademy.minidooray_task.exception;

public class TagAlreadyExistsException extends RuntimeException{
    public TagAlreadyExistsException(String message) {
        super(message);
    }
}
