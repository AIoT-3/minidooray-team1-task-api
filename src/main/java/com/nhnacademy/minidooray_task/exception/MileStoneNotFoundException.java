package com.nhnacademy.minidooray_task.exception;

public class MileStoneNotFoundException extends RuntimeException {
    public MileStoneNotFoundException(Long id) {
        super("해당 마일스톤을 찾을 수 없습니다. ID: " + id);
    }
}
