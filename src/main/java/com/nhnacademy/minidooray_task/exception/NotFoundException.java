package com.nhnacademy.minidooray_task.exception;

//존재하지않음
public class NotFoundException extends BaseException {
    public NotFoundException(Long message) {
        super("존재하지 않는 프로젝트입니다. ID: "+message);
    }
}