package com.nhnacademy.minidooray_task.repository;

import com.nhnacademy.minidooray_task.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project save(Project project);
    Optional<Project> findById(Long projectId);

}
