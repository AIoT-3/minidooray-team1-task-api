package com.example.spring_boot_jpa.service;

import com.example.spring_boot_jpa.dto.CommentDto;
import com.example.spring_boot_jpa.entity.Comment;
import com.example.spring_boot_jpa.entity.Task;
import com.example.spring_boot_jpa.exeption.ForbiddenException;
import com.example.spring_boot_jpa.exeption.NotFoundException;
import com.example.spring_boot_jpa.repository.CommentRepository;
import com.example.spring_boot_jpa.repository.TaskRepository;
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
                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
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