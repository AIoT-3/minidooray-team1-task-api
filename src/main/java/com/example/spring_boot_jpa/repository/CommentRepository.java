package com.example.spring_boot_jpa.repository;

import com.example.spring_boot_jpa.entity.Comment;
import com.example.spring_boot_jpa.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByIdAndTask(Long id, Task task);
}
