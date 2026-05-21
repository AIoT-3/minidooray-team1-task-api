package com.nhnacademy.minidooray_task.exception;

// 프로젝트가 존재하지 않을 때 던질 예외
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long projectId) {
        super("존재하지 않는 프로젝트입니다. ID: " + projectId);
    }
}