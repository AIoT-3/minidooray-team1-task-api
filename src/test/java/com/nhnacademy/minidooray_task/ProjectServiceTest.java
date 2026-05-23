package com.nhnacademy.minidooray_task;

import com.nhnacademy.minidooray_task.dto.ProjectMemberResponseDto;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.entity.ProjectStatus;
import com.nhnacademy.minidooray_task.exception.InvalidProjectMemberException;
import com.nhnacademy.minidooray_task.exception.NotFoundException;
import com.nhnacademy.minidooray_task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray_task.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import com.nhnacademy.minidooray_task.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    private ProjectService projectService; // 테스트 대상 서비스 객체

    @Test
    @DisplayName("1. 프로젝트 생성 - 기본값(ACTIVE) 세팅 검증")
    void createProject() {
        // Given (상황 설정)
        Project project = new Project();
        project.setName("테스트 미니두레이");
        when(projectRepository.save(any(Project.class))).thenAnswer(i -> i.getArgument(0));

        // When (동작 실행)
        Project result = projectService.createProject(project);

        // Then (결과 확인)
        assertThat(result.getStatus()).isEqualTo(ProjectStatus.ACTIVE);
        assertThat(result.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("2. 없는 프로젝트에 멤버 추가 시 예외(404) 검증")
    void addProjectMember_exception() {
        // Given (999번 프로젝트는 존재하지 않음)
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then (실행과 동시에 에러 검증)
        assertThatThrownBy(() -> projectService.addProjectMember(999L, 100L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("3. 특정 프로젝트의 모든 멤버 리스트 DTO 조회 검증")
    void getProjectMembers() {
        // Given (조회할 멤버 엔티티 세팅)
        Project project = new Project();
        project.setId(1L);
        ProjectMember m = new ProjectMember();
        m.setProject(project);
        m.setMemberId(100L);
        when(projectMemberRepository.findByProjectId(1L)).thenReturn(List.of(m));

        // When
        List<ProjectMemberResponseDto> list = projectService.getProjectMembers(1L);

        // Then
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getMemberId()).isEqualTo(100L);
    }

    @Test
    @DisplayName("4. 복합키 단건 조회 시 없는 멤버면 예외 검증")
    void getProjectMember_exception() {
        // Given (1번 방의 999번 유저는 없음)
        ProjectMember.Pk pk = new ProjectMember.Pk(1L, 999L);
        when(projectMemberRepository.findById(pk)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> projectService.getProjectMember(1L, 999L))
                .isInstanceOf(InvalidProjectMemberException.class);
    }
}