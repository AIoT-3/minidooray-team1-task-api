package com.nhnacademy.minidooray_task.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(Long message) {
        super("마일스톤이 존재하지 않습니다. ID: " + message);
    }
}
