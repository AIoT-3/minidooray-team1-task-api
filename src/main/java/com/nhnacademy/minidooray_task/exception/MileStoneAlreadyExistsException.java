package com.nhnacademy.minidooray_task.exception;

public class MileStoneAlreadyExistsException extends RuntimeException {
    public MileStoneAlreadyExistsException(String message) {
        super(message);
    }
}