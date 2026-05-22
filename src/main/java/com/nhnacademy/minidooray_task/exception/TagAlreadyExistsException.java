package com.nhnacademy.minidooray_task.exception;

public class TagAlreadyExistsException extends RuntimeException{
    public TagAlreadyExistsException(String message) {
        super("이미 존재하는 태그 이름입니다. : " + message);
    }
}
