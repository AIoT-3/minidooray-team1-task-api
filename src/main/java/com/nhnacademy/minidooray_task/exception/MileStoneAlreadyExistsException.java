package com.nhnacademy.minidooray_task.exception;

public class MileStoneAlreadyExistsException extends RuntimeException {
    public MileStoneAlreadyExistsException(String milestoneName) {
        super("이미 존재하는 마일스톤 이름입니다: " + milestoneName);
    }
}