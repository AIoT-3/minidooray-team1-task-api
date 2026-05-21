package com.example.task_api.repository;

import com.example.task_api.entity.Project;
import com.example.task_api.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project save(Project project);

    Optional<Project> findById(Long projectId);

}
