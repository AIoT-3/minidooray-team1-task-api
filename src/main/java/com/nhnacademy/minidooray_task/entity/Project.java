package com.nhnacademy.minidooray_task.entity;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db가 알아서 1,2,3..증가
    private Long id;

    @Column(nullable = false,length =100 )
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVE, DORMANT, TERMINATED, PLANNING
    }

    //프로젝트를 생성한 사용자의 id를 저장하여 '관리자'로 임명
    @Column(name="admin_id", nullable = false)
    private Long adminId;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist // 새로운 프로젝트가 생성되어 데베에 처음 저장될때 현재 날짜와 시간을 자동으로 채워주는 JPA의 자동 타이머주입 기능임.
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Project(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status != null ? status : Status.ACTIVE;
    }

    public void update(String name, Status status) {
        this.name = name;
        this.status = status;
    }
}
