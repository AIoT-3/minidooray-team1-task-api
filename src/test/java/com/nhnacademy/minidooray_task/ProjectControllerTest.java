package com.nhnacademy.minidooray_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray_task.controller.ProjectController;
import com.nhnacademy.minidooray_task.dto.ProjectMemberResponseDto;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.entity.ProjectStatus; // 💡 Enum 패키지 임포트
import com.nhnacademy.minidooray_task.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProjectService projectService;

    @Test
    @DisplayName("1. 프로젝트 생성 성공 테스트")
    void createProject_success() throws Exception {
        // 💡 protected 생성자를 피해가기 위해 Mockito로 리턴받을 가짜 객체 설정
        Project project = new Project();

        // 서비스 로직 내부에서 기본값으로 ACTIVE가 주입되므로 동일하게 매칭
        project.setName("미니두레이 프로젝트");
        project.setStatus(ProjectStatus.ACTIVE); // 실제 존재하는 ACTIVE로 수정

        when(projectService.createProject(any(Project.class))).thenReturn(project);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"미니두레이 프로젝트\",\"status\":\"ACTIVE\"}")) //요청 본문도 매칭
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("미니두레이 프로젝트"));
    }

    @Test
    @DisplayName("2. 프로젝트 멤버 추가 성공 테스트")
    void addProjectMember_success() throws Exception {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setMemberId(100L);

        when(projectService.addProjectMember(1L, 100L)).thenReturn(projectMember);

        mockMvc.perform(post("/projects/1/members/100"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").value(100));
    }

    @Test
    @DisplayName("3. 특정 프로젝트의 모든 멤버 조회 테스트")
    void getProjectMembers_success() throws Exception {
        List<ProjectMemberResponseDto> responseList = List.of(
                new ProjectMemberResponseDto(1L, 100L)
        );

        when(projectService.getProjectMembers(1L)).thenReturn(responseList);

        mockMvc.perform(get("/projects/1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[0].memberId").value(100));
    }

    @Test
    @DisplayName("4. 복합키를 이용한 특정 멤버 단건 조회 테스트")
    void getProjectMember_success() throws Exception {
        ProjectMemberResponseDto responseDto = new ProjectMemberResponseDto(1L, 100L);

        when(projectService.getProjectMember(1L, 100L)).thenReturn(responseDto);

        mockMvc.perform(get("/projects/1/members/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.memberId").value(100));
    }
}