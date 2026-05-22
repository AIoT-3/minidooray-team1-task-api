package com.nhnacademy.minidooray_task.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nhnacademy.minidooray_task.dto.TagCreateRequest;
import com.nhnacademy.minidooray_task.dto.TagDto;
import com.nhnacademy.minidooray_task.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{project-id}/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> createTag(
            @Valid @RequestBody TagCreateRequest request,
            @PathVariable("project-id") String projectId) {
        TagDto response = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(
            @PathVariable Long id,
            @PathVariable("project-id") String projectId) {
        TagDto response = tagService.getTag(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(
            @PathVariable("project-id") String projectId) {
        List<TagDto> response = tagService.getAllTags();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long id,
            @PathVariable("project-id") String projectId) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}