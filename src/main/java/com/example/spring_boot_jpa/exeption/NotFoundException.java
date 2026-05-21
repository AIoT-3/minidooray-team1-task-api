package com.example.spring_boot_jpa.exeption;

//존재하지않음
public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }
}