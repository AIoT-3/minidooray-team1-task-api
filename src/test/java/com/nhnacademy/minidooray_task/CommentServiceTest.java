package com.nhnacademy.minidooray_task;

import com.nhnacademy.minidooray_task.dto.CommentDto;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.entity.Task;
import com.nhnacademy.minidooray_task.repository.CommentRepository;
import com.nhnacademy.minidooray_task.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import com.nhnacademy.minidooray_task.repository.TaskRepository;
import com.nhnacademy.minidooray_task.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Project project;
    private Task task;
    private Long memberId = 1L;

    @BeforeEach
    void setUp() {
        Project p = Project.builder()
                .name("테스트 프로젝트")
                .status(Project.Status.ACTIVE)
                .build();

        p.setAdminId(memberId);

        project = projectRepository.save(p);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
        projectMember.setMemberId(memberId);

        ProjectMember savedMember =
                projectMemberRepository.save(projectMember);

        task = taskRepository.save(
                Task.builder()
                        .project(project)
                        .projectMember(savedMember)
                        .title("테스트 Task")
                        .content("내용")
                        .build()
        );
    }

    @Test
    @DisplayName("댓글 생성 성공")
    void create_success() {
        CommentDto.Create request = new CommentDto.Create();

        ReflectionTestUtils.setField(request, "content", "댓글 내용");

        CommentDto.Response response =
                commentService.create(
                        project.getId(),
                        task.getId(),
                        request,
                        memberId
                );

        assertThat(response.getContent()).isEqualTo("댓글 내용");
    }


    @Test
    @DisplayName("댓글 수정 성공")
    void update_success() {
        CommentDto.Create createRequest = new CommentDto.Create();

        ReflectionTestUtils.setField(createRequest, "content", "기존 댓글");

        CommentDto.Response created =
                commentService.create(
                        project.getId(),
                        task.getId(),
                        createRequest,
                        memberId
                );

        CommentDto.Update updateRequest = new CommentDto.Update();

        ReflectionTestUtils.setField(updateRequest, "content", "수정 댓글");

        CommentDto.Response response =
                commentService.update(
                        project.getId(),
                        task.getId(),
                        created.getId(),
                        updateRequest,
                        memberId
                );

        assertThat(response.getContent()).isEqualTo("수정 댓글");
    }

    // 테스트 코드 상단 필드에 추가 필요

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_success() {
        CommentDto.Create request = new CommentDto.Create();
        ReflectionTestUtils.setField(request, "content", "삭제할 댓글");

        CommentDto.Response created =
                commentService.create(project.getId(), task.getId(), request, memberId);

        // 삭제 수행
        commentService.delete(project.getId(), task.getId(), created.getId(), memberId);

        // ✅ repository를 직접 조회하여 삭제되었는지 검증
        boolean exists = commentRepository.findById(created.getId()).isPresent();
        assertThat(exists).isFalse();
    }
}