package com.nhnacademy.minidooray_task.repository;

import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

//    @Query("select i"+" from Task i"+" left outer join fetch i.~~ oi"+" inner join fetch oi.~~~ o")
    List<Task> findByProject(Project project);

    Optional<Task> findByIdAndProject(Long id, Project project);
}

