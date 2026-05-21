package com.nhnacademy.minidooray_task.repository;

import com.nhnacademy.minidooray_task.entity.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MileStoneRepository extends JpaRepository<MileStone, Long> {
    Optional<MileStone> findByName(String name);
    boolean existsByName(String name);
}