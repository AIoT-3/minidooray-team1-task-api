package com.example.spring_boot_jpa.exeption;

//권한없음
public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(message);
    }
}
