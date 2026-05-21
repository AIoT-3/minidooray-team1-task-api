package com.example.spring_boot_jpa.controller;

import com.example.spring_boot_jpa.dto.TaskDto;
import com.example.spring_boot_jpa.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto.Response> create(
            @PathVariable Long projectId,
            @RequestBody TaskDto.Create request,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.create(projectId, request, memberId));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto.Response>> list(
            @PathVariable Long projectId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.list(projectId, memberId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto.Response> content(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.content(projectId, taskId, memberId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto.Response> update(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskDto.Update request,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(taskService.update(projectId, taskId, request, memberId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestParam Long memberId) {
        taskService.delete(projectId, taskId, memberId);
        return ResponseEntity.noContent().build();
    }
}