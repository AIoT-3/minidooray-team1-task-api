package com.nhnacademy.minidooray_task.repository;

import com.nhnacademy.minidooray_task.entity.Comment;
import com.nhnacademy.minidooray_task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByIdAndTask(Long id, Task task);
}
