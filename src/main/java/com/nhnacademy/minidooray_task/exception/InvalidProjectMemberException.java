package com.nhnacademy.minidooray_task.exception;

// 참여하지 않은 멤버이거나 권한이 없을 때 던질 예외
public class InvalidProjectMemberException extends RuntimeException {
    public InvalidProjectMemberException() {
        super("해당 프로젝트에 참여하지 않은 멤버이거나 잘못된 정보입니다.");
    }
}