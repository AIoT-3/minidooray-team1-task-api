package com.nhnacademy.minidooray_task.exception;

//요소부족
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}