package com.nhnacademy.minidooray_task.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "project_id", nullable = false)
//    private Project project;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private ProjectMember projectMember;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private MileStone milestone;

    private String title;
    private String content;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Builder
    public Task(Long id, String title, String content,Project project, ProjectMember projectMember, MileStone milestone) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
//        this.project = project;
//        this.projectMember = projectMember;
        this.milestone = milestone;
    }

    public void update(String title, String content, MileStone milestone) {
        this.title = title;
        this.content = content;
        this.milestone = milestone;
        this.updatedAt = ZonedDateTime.now();

    }
}
