package com.example.task_api.repository;

import com.example.task_api.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProjectMemberRepository extends JpaRepository<ProjectMember,ProjectMember.Pk> {
    // 💡 프로젝트 ID를 기반으로 멤버 리스트 출석부를 긁어오기 위한 커스텀 메서드 정의!
    List<ProjectMember> findByProjectId(Long projectId);
}
