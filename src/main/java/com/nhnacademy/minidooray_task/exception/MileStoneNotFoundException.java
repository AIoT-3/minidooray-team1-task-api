package com.nhnacademy.minidooray_task.exception;

public class MileStoneNotFoundException extends RuntimeException {
    public MileStoneNotFoundException(Long message) {
        super("마일스톤이 존재하지 않습니다: "+message);
    }
}
