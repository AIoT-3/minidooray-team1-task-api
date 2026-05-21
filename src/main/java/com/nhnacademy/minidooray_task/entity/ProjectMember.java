package com.nhnacademy.minidooray_task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name="project_members")
@Getter
@Setter
@NoArgsConstructor
@IdClass(ProjectMember.Pk.class)
public class ProjectMember {

    @Id //Fk이자 Pk
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id",nullable = false)
    private Project project;



    @Id //Pk
    @Column(name="member_id", nullable = false)
    private Long memberId;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Pk implements Serializable{
        private Long project;
        private Long memberId;
    }
}

