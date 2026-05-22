package com.nhnacademy.minidooray_task.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "member_id", referencedColumnName = "member_id",
                    insertable = false, updatable = false),
            @JoinColumn(name = "project_id", referencedColumnName = "project_id",
                    insertable = false, updatable = false) // ✅ 중복 컬럼 읽기 전용
    })
    private ProjectMember projectMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "member_id", referencedColumnName = "member_id",
                    insertable = false, updatable = false),
            @JoinColumn(name = "project_id", referencedColumnName = "project_id",
                    insertable = false, updatable = false) // ✅ 중복 컬럼 읽기 전용
    })
    private ProjectMember projectMember;

    @ManyToMany(mappedBy = "tasks") // 기존 "milestone"에서 "tasks"로 수정
    private List<Tag> tags = new ArrayList<>();

    public void removeMilestone() {
        this.milestone = null;
    }
    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private MileStone milestone;

    private String title;
    private String content;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public java.util.List<Tag> getTags() {
        if (this.tags == null) {
            this.tags = new java.util.ArrayList<>();
        }
        return this.tags;
    }

    @Builder
    public Task(Long id, String title, String content,Project project, ProjectMember projectMember, MileStone milestone) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
        this.project = project;
        this.projectMember = projectMember;
        this.milestone = milestone;
    }

    public void update(String title, String content, MileStone milestone) {
        this.title = title;
        this.content = content;
        this.milestone = milestone;
        this.updatedAt = ZonedDateTime.now();

    }
}
