package com.nhnacademy.minidooray_task.service;

import com.nhnacademy.minidooray_task.dto.TaskDto;
import com.nhnacademy.minidooray_task.entity.MileStone;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.entity.Task;
import com.nhnacademy.minidooray_task.exception.MileStoneNotFoundException;
import com.nhnacademy.minidooray_task.exception.NotFoundException;
import com.nhnacademy.minidooray_task.repository.MileStoneRepository;
import com.nhnacademy.minidooray_task.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import com.nhnacademy.minidooray_task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final MileStoneRepository milestoneRepository;

    @Transactional
    public TaskDto.Response create(Long projectId, TaskDto.Create request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(projectId));
        ProjectMember projectMember = projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(() -> new NotFoundException(memberId));
        MileStone milestone = request.getMilestone() != null
                ? milestoneRepository.findById(request.getMilestone())
                .orElseThrow(() -> new MileStoneNotFoundException(request.getMilestone()))
                : null;

        Task task = Task.builder()
                .project(project)
                .projectMember(projectMember)
                .milestone(milestone)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return TaskDto.Response.from(taskRepository.save(task));
    }

    public List<TaskDto.Response> list(Long projectId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(projectId));
        projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(() -> new NotFoundException(memberId));

        return taskRepository.findByProject(project)
                .stream()
                .map(TaskDto.Response::from)
                .toList();
    }

    public TaskDto.Response content(Long projectId, Long taskId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(projectId));
        projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(() -> new NotFoundException(memberId));
        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new NotFoundException(taskId));

        return TaskDto.Response.from(task);
    }

    @Transactional
    public TaskDto.Response update(Long projectId, Long taskId, TaskDto.Update request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(projectId));
        projectMemberRepository.findByProjectIdAndMemberId(projectId,memberId)
                .orElseThrow(() -> new NotFoundException(memberId));

        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new NotFoundException(taskId));
        MileStone milestone = request.getMilestone() != null
                ? milestoneRepository.findById(request.getMilestone())
                .orElseThrow(() -> new NotFoundException(request.getMilestone()))
                : null;

        task.update(request.getTitle(), request.getContent(), milestone);
        return TaskDto.Response.from(task);
    }

    @Transactional
    public void delete(Long projectId, Long taskId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(projectId));
        projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(() -> new NotFoundException(memberId));

        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new NotFoundException(taskId));

        taskRepository.delete(task);
    }
}
