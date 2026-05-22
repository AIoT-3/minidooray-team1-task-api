package com.nhnacademy.minidooray_task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "task_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> tasks = new ArrayList<>();

    @Builder
    public Tag(Long id, Project project, String name) {
        this.id = id;
        this.project = project;
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

    @PreRemove
    private void removeAssociations() {
        for (Task task : tasks) {
            task.getTags().remove(this);
        }
    }
}
