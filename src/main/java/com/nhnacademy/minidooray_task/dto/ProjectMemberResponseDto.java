package com.nhnacademy.minidooray_task.dto;

import com.nhnacademy.minidooray_task.entity.ProjectMember;

public class ProjectMemberResponseDto {
    private Long projectId;
    private Long memberId;

    public ProjectMemberResponseDto(Long projectId, Long memberId){
        this.projectId=projectId;
        this.memberId=memberId;
    }

    public Long getProjectId(){
        return projectId;
    }

    public Long getMemberId(){
        return memberId;
    }
}
