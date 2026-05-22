package com.nhnacademy.minidooray_task.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(Long id) {
        super("해당 태그를 찾을 수 없습니다. ID: " + id);
    }
}
