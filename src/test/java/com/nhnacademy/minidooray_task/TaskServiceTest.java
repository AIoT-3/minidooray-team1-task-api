package com.nhnacademy.minidooray_task;

import com.nhnacademy.minidooray_task.dto.TaskDto;
import com.nhnacademy.minidooray_task.entity.MileStone;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.exception.NotFoundException;
import com.nhnacademy.minidooray_task.repository.MileStoneRepository;
import com.nhnacademy.minidooray_task.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import com.nhnacademy.minidooray_task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private ProjectMemberRepository projectMemberRepository;
    @Autowired private MileStoneRepository mileStoneRepository;

    private Project project;
    private Long memberId = 1L;

    @BeforeEach
    void setUp() {
        // Project - Builder에 adminId 없으니 Setter로 설정
        Project p = Project.builder().name("테스트 프로젝트").status(Project.Status.ACTIVE).build();
        p.setAdminId(memberId);
        project = projectRepository.save(p);

        // ProjectMember - @Builder 없으니 Setter 사용
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
        projectMember.setMemberId(memberId);
        projectMemberRepository.save(projectMember);
    }

    @Test
    @DisplayName("Task 생성 성공")
    void create_success() {
        TaskDto.Create request = new TaskDto.Create();

        TaskDto.Response response = taskService.create(project.getId(), request, memberId);

        assertThat(response.getTitle()).isEqualTo("테스트 Task");
        assertThat(response.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("Task 생성 실패 - 존재하지 않는 프로젝트")
    void create_fail_projectNotFound() {
        TaskDto.Create request = new TaskDto.Create();

        assertThatThrownBy(() -> taskService.create(999L, request, memberId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Task 생성 실패 - 프로젝트 멤버가 아님")
    void create_fail_notProjectMember() {
        TaskDto.Create request = new TaskDto.Create();

        assertThatThrownBy(() -> taskService.create(project.getId(), request, 999L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Task 생성 성공 - 마일스톤 포함")
    void create_success_withMilestone() {
        MileStone mileStone = mileStoneRepository.save(
                MileStone.builder().name("마일스톤").project(project).build()
        );
        TaskDto.Create request = new TaskDto.Create();

        TaskDto.Response response = taskService.create(project.getId(), request, memberId);

        assertThat(response.getMilestoneId()).isEqualTo(mileStone.getId());
    }

    @Test
    @DisplayName("Task 목록 조회 성공")
    void list_success() {
        taskService.create(project.getId(), new TaskDto.Create(), memberId);
        taskService.create(project.getId(), new TaskDto.Create(), memberId);

        List<TaskDto.Response> responses = taskService.list(project.getId(), memberId);

        assertThat(responses).hasSameClassAs(2);
    }

    @Test
    @DisplayName("Task 상세 조회 성공")
    void content_success() {
        TaskDto.Response created = taskService.create(
                project.getId(), new TaskDto.Create(), memberId);

        TaskDto.Response response = taskService.content(project.getId(), created.getId(), memberId);

        assertThat(response.getTitle()).isEqualTo("테스트 Task");
    }

    @Test
    @DisplayName("Task 상세 조회 실패 - 존재하지 않는 Task")
    void content_fail_taskNotFound() {
        assertThatThrownBy(() -> taskService.content(project.getId(), 999L, memberId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Task 수정 성공")
    void update_success() {
        TaskDto.Response created = taskService.create(
                project.getId(), new TaskDto.Create(), memberId);

        TaskDto.Response response = taskService.update(
                project.getId(), created.getId(),
                new TaskDto.Update(), memberId);

        assertThat(response.getTitle()).isEqualTo("수정된 제목");
        assertThat(response.getContent()).isEqualTo("수정된 내용");
    }

    @Test
    @DisplayName("Task 수정 실패 - 존재하지 않는 Task")
    void update_fail_taskNotFound() {
        assertThatThrownBy(() -> taskService.update(
                project.getId(), 999L,
                new TaskDto.Update(), memberId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Task 삭제 성공")
    void delete_success() {
        TaskDto.Response created = taskService.create(
                project.getId(), new TaskDto.Create(), memberId);

        taskService.delete(project.getId(), created.getId(), memberId);

        assertThatThrownBy(() -> taskService.content(project.getId(), created.getId(), memberId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Task 삭제 실패 - 존재하지 않는 Task")
    void delete_fail_taskNotFound() {
        assertThatThrownBy(() -> taskService.delete(project.getId(), 999L, memberId))
                .isInstanceOf(NotFoundException.class);
    }
}