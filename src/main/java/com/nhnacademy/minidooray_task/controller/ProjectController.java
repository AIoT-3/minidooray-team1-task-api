package com.nhnacademy.minidooray_task.controller;

import com.nhnacademy.minidooray_task.dto.ProjectMemberResponseDto;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    //프로젝트 생성 창구
    //*POST /api/projects
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project createProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(createProject);
    }

    //프로젝트에 멤버 추가 창구(복합키 활용된 서비스 호출)
    //* POST /api/projects/{projectId}/members
    @PostMapping("/{projectId}/members")
    public ResponseEntity<ProjectMember> addProjectMember(@PathVariable("projectId") Long projectId,
                                                          @PathVariable("memberId") Long memberId) {
        ProjectMember projectMember = projectService.addProjectMember(projectId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMember);
    }


    //특정 프로젝트의 모든 멤버 조회
    //* GET /api/projects/{projectId}/members
    @GetMapping("/{projectId}/members")
    public ResponseEntity<List<ProjectMemberResponseDto>> getProjectMembers(@PathVariable("projectId") Long projectId) {
        List<ProjectMember> members = projectService.getProjectMembers(projectId);

        List<ProjectMemberResponseDto> response = members.stream()
                .map(m -> new ProjectMemberResponseDto(m.getProject().getId(), m.getMemberId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    //복합키를 이용한 특정 프로젝트의 특정 멤버 단건 조회
    //* GET /api/projects/{projectId}/members/{memberId}
    @GetMapping("/{project-id}/members/{member-id}")
    public  ResponseEntity<ProjectMemberResponseDto> getProjectMember(
            @PathVariable("project-id") Long projectId,
            @PathVariable("member-id") Long memberId){
        ProjectMember m=projectService.getProjectMember(projectId, memberId);
        ProjectMemberResponseDto response = new ProjectMemberResponseDto(m.getProject().getId(), m.getMemberId());
        return ResponseEntity.ok(response);
    }
}

