package com.example.spring_boot_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private ProjectMember projectMember;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Builder
    public Comment(String content, Task task, ProjectMember projectMember) {
        this.content = content;
        this.task = task;
        this.projectMember = projectMember;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    public void update(String content) {
        this.content = content;
        this.updatedAt = ZonedDateTime.now();
    }
}

