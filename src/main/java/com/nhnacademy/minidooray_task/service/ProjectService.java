package com.nhnacademy.minidooray_task.service;

import com.nhnacademy.minidooray_task.dto.ProjectMemberResponseDto;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.ProjectMember;
import com.nhnacademy.minidooray_task.exception.InvalidProjectMemberException;
import com.nhnacademy.minidooray_task.exception.NotFoundException;
import com.nhnacademy.minidooray_task.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import static com.nhnacademy.minidooray_task.entity.ProjectStatus.ACTIVE;

@Service
@RequiredArgsConstructor //repository주입 위한 롬복
@Transactional(readOnly = true)//기본적으로 조회는 안전하게 readOnly로 작동함
public class ProjectService {
    // 두 repository 주입받아 사용
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    //새로운 프로젝트 생성하는 로직
    @Transactional
    public Project createProject(Project project){
        if (project.getCreatedAt() == null) {
            project.setCreatedAt(LocalDateTime.now());
        }
        // 💡 [보완] 기본 상태값이 비어있다면 y777u7u초기 상태를 지정해줍니다.
        if (project.getStatus() == null) {
            project.setStatus(ACTIVE);
        }
        return projectRepository.save(project);
    }

    //프로젝트에 멤버를 주가하는 로직(복합키 사용됨)
    @Transactional
    public ProjectMember addProjectMember(Long projectId, Long memberId) {
        //1. 먼저 해당 프로젝트가 존재하는 방인지 확인하고 가져오기
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(projectId));
        //2. projectMember를 조립한다.
        ProjectMember projectMember=new ProjectMember();
        projectMember.setProject(project);
        projectMember.setMemberId(memberId);
        //3. 조립된 멤버 정보를 데베에 저장
        return projectMemberRepository.save(projectMember);
    }

    //특정 프로젝트에 참여중인 모든 멤버 리스트를 조회하는 로직
    @Transactional(readOnly = true)
    public List<ProjectMemberResponseDto> getProjectMembers(Long projectId){
        List<ProjectMember> members=projectMemberRepository.findByProjectId(projectId);
        return members.stream()
                .map(m-> new ProjectMemberResponseDto(m.getProject().getId(),m.getMemberId()))
                .toList();
    }

    //특정 멤버가 자신이 참여중인 프로젝트 목록만 싹 긁어올때 쓰는 자물쇠(복합키) 단건 조회 로직
    public ProjectMemberResponseDto getProjectMember(Long projectId, Long memberId) {
        ProjectMember.Pk pk = new ProjectMember.Pk(projectId, memberId);
        ProjectMember m= projectMemberRepository.findById(pk)
                .orElseThrow(InvalidProjectMemberException::new);

        return new ProjectMemberResponseDto(m.getProject().getId(), m.getMemberId());
    }

}

