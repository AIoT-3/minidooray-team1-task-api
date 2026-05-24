package com.nhnacademy.minidooray_task.controller;

import com.nhnacademy.minidooray_task.dto.CommentDto;
import com.nhnacademy.minidooray_task.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto.Response> create(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody CommentDto.Create request,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(commentService.create(projectId, taskId, request, memberId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto.Response> update(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @RequestBody CommentDto.Update request,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(commentService.update(projectId, taskId, commentId, request, memberId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @RequestParam Long memberId) {
        commentService.delete(projectId, taskId, commentId, memberId);
        return ResponseEntity.noContent().build();
    }
}