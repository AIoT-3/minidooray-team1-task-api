package com.nhnacademy.minidooray_task.dto;

import com.nhnacademy.minidooray_task.entity.Project.Status;
import lombok.Getter;

@Getter
public class ProjectDto {
    private final Long id;
    private final String name;
    private final Status status;
    private Long adminId;
    private String createdAt;


    public ProjectDto(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}