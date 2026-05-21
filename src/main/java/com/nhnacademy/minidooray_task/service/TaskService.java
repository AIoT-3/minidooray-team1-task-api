//package com.nhnacademy.minidooray_task.service;
//
//import com.nhnacademy.minidooray_task.dto.TaskDto;
//import com.nhnacademy.minidooray_task.entity.Project;
//import com.nhnacademy.minidooray_task.entity.ProjectMember;
//import com.nhnacademy.minidooray_task.entity.Task;
//import com.nhnacademy.minidooray_task.exception.NotFoundException;
//import com.nhnacademy.minidooray_task.repository.MileStoneRepository;
//import com.nhnacademy.minidooray_task.repository.ProjectMemberRepository;
//import com.nhnacademy.minidooray_task.repository.ProjectRepository;
//import com.nhnacademy.minidooray_task.repository.TaskRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class TaskService {
//
//    private final TaskRepository taskRepository;
//    private final ProjectRepository projectRepository;
//    private final ProjectMemberRepository projectMemberRepository;
//    private final MileStoneRepository milestoneRepository;
//
//    @Transactional
//    public TaskDto.Response create(Long projectId, TaskDto.Create request, Long memberId) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
//        ProjectMember projectMember = projectMemberRepository.findByProjectAndMemberId(project, memberId)
//                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));
//        Milestone milestone = request.getMilestone() != null
//                ? milestoneRepository.findById(request.getMilestone())
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 마일스톤입니다."))
//                : null;
//
//        Task task = Task.builder()
//                .project(project)
//                .projectMember(projectMember)
//                .milestone(milestone)
//                .title(request.getTitle())
//                .content(request.getContent())
//                .build();
//
//        return TaskDto.Response.from(taskRepository.save(task));
//    }
//
//    public List<TaskDto.Response> list(Long projectId, Long memberId) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
//        projectMemberRepository.findByProjectAndMemberId(project, memberId)
//                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));
//
//        return taskRepository.findByProject(project)
//                .stream()
//                .map(TaskDto.Response::from)
//                .toList();
//    }
//
//    public TaskDto.Response content(Long projectId, Long taskId, Long memberId) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
//        projectMemberRepository.findByProjectAndMemberId(project, memberId)
//                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));
//
//        Task task = taskRepository.findByIdAndProject(taskId, project)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 Task입니다."));
//
//        return TaskDto.Response.from(task);
//    }
//
//    @Transactional
//    public TaskDto.Response update(Long projectId, Long taskId, TaskDto.Update request, Long memberId) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
//        projectMemberRepository.findByProjectAndMemberId(project, memberId)
//                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));
//
//        Task task = taskRepository.findByIdAndProject(taskId, project)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 Task입니다."));
//        Milestone milestone = request.getMilestone() != null
//                ? milestoneRepository.findById(request.getMilestone())
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 마일스톤입니다."))
//                : null;
//
//        task.update(request.getTitle(), request.getContent(), milestone);
//        return TaskDto.Response.from(task);
//    }
//
//    @Transactional
//    public void delete(Long projectId, Long taskId, Long memberId) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 프로젝트입니다."));
//        projectMemberRepository.findByProjectAndMemberId(project, memberId)
//                .orElseThrow(() -> new NotFoundException("프로젝트 멤버가 아닙니다."));
//
//        Task task = taskRepository.findByIdAndProject(taskId, project)
//                .orElseThrow(() -> new NotFoundException("존재하지 않는 Task입니다."));
//
//        taskRepository.delete(task);
//    }
//}
