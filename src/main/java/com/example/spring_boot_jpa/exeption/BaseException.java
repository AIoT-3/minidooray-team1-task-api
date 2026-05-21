package com.example.spring_boot_jpa.exeption;

//요소부족
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}