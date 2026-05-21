package com.nhnacademy.minidooray_task.repository;

import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface ProjectMemberRepository extends JpaRepository<ProjectMember,ProjectMember.Pk> {
    // projectId + memberId 두 조건으로 단건 조회
    Optional<ProjectMember> findByProjectIdAndMemberId(Long projectId, Long memberId);

    // projectId로 멤버 목록 전체 조회
    List<ProjectMember> findByProjectId(Long projectId);

}
