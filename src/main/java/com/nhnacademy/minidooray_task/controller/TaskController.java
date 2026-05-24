package com.nhnacademy.minidooray_task.controller;

import com.nhnacademy.minidooray_task.dto.TaskDto;
import com.nhnacademy.minidooray_task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{projectId}/tasks/{taskId}")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto.Response> create(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskDto.Create request,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.create(projectId, request, memberId));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto.Response>> list(
            @PathVariable Long projectId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.list(projectId, memberId));
    }

    @GetMapping("/{task-id}")
    public ResponseEntity<TaskDto.Response> content(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.content(projectId, taskId, memberId));
    }

    @PutMapping("/{task-id}")
    public ResponseEntity<TaskDto.Response> update(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskDto.Update request,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.update(projectId, taskId, request, memberId));
    }

    @DeleteMapping("/{task-id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestParam Long memberId) {
        taskService.delete(projectId, taskId, memberId);
        return ResponseEntity.noContent().build();
    }
}
