package com.nhnacademy.minidooray_task.service;

import com.nhnacademy.minidooray_task.dto.CommentDto;
import com.nhnacademy.minidooray_task.entity.Comment;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.entity.Task;
import com.nhnacademy.minidooray_task.exception.ForbiddenException;
import com.nhnacademy.minidooray_task.exception.NotFoundException;
import com.nhnacademy.minidooray_task.repository.CommentRepository;
import com.nhnacademy.minidooray_task.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import com.nhnacademy.minidooray_task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Transactional
    public CommentDto.Response create(Long projectId, Long taskId, CommentDto.Create request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
        ProjectMember projectMember = projectMemberRepository.findByProjectAndMemberId(project, memberId)
                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));
        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 Task입니다."));

        Comment comment = Comment.builder()
                .task(task)
                .projectMember(projectMember)
                .content(request.getContent())
                .build();

        return CommentDto.Response.from(commentRepository.save(comment));
    }

    @Transactional
    public CommentDto.Response update(Long projectId, Long taskId, Long commentId, CommentDto.Update request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow();
        ProjectMember projectMember = projectMemberRepository.findByProjectAndMemberId(project, memberId)
                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));

        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 Task입니다."));

        Comment comment = commentRepository.findByIdAndTask(commentId, task)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 Comment입니다."));

        if (!comment.getProjectMember().getId().equals(projectMember.getId())) {
            throw new ForbiddenException("수정 권한이 없습니다.");
        }

        comment.update(request.getContent());
        return CommentDto.Response.from(comment);
    }

    @Transactional
    public void delete(Long projectId, Long taskId, Long commentId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
        ProjectMember projectMember = projectMemberRepository.findByProjectAndMemberId(project, memberId)
                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));

        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 Task입니다."));

        Comment comment = commentRepository.findByIdAndTask(commentId, task)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 Comment입니다."));

        if (!comment.getProjectMember().getId().equals(projectMember.getId())) {
            throw new ForbiddenException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}