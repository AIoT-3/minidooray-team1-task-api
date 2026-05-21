package com.nhnacademy.minidooray_task.exception;

//권한없음
public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(message);
    }
}
