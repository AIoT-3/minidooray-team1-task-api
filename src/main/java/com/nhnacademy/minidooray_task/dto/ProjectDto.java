package com.nhnacademy.minidooray_task.dto;
import com.nhnacademy.minidooray_task.entity.ProjectStatus;
import lombok.Getter;

@Getter
public class ProjectDto {
    private final Long id;
    private final String name;
    private final ProjectStatus status;
    private Long adminId;
    private String createdAt;


    public ProjectDto(Long id, String name, ProjectStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}