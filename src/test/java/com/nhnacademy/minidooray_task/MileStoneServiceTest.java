package com.nhnacademy.minidooray_task;

import com.nhnacademy.minidooray_task.dto.MileStoneCreateRequest;
import com.nhnacademy.minidooray_task.dto.MileStoneDto;
import com.nhnacademy.minidooray_task.dto.MileStoneUpdateRequest;
import com.nhnacademy.minidooray_task.entity.MileStone;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.exception.MileStoneAlreadyExistsException;
import com.nhnacademy.minidooray_task.exception.MileStoneNotFoundException;
import com.nhnacademy.minidooray_task.exception.NotFoundException;
import com.nhnacademy.minidooray_task.repository.MileStoneRepository;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import com.nhnacademy.minidooray_task.service.MileStoneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MileStoneServiceTest {

    @Mock
    private MileStoneRepository mileStoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private MileStoneService mileStoneService;

    @Test
    @DisplayName("마일스톤 생성 성공")
    void createMileStone_Success() {
        MileStoneCreateRequest request = new MileStoneCreateRequest("Sprint 1", 1L);
        Project project = Project.builder().build();
        MileStone savedMileStone = MileStone.builder().id(10L).name("Sprint 1").project(project).build();

        given(mileStoneRepository.existsByName("Sprint 1")).willReturn(false);
        given(projectRepository.findById(1L)).willReturn(Optional.of(project));
        given(mileStoneRepository.save(any(MileStone.class))).willReturn(savedMileStone);

        MileStoneDto result = mileStoneService.createMileStone(request);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("Sprint 1");
    }

    @Test
    @DisplayName("마일스톤 생성 실패 - 이미 존재하는 이름")
    void createMileStone_ThrowsMileStoneAlreadyExistsException() {
        MileStoneCreateRequest request = new MileStoneCreateRequest("Duplicate Name", 1L);
        given(mileStoneRepository.existsByName("Duplicate Name")).willReturn(true);

        assertThatThrownBy(() -> mileStoneService.createMileStone(request))
                .isInstanceOf(MileStoneAlreadyExistsException.class);
    }

    @Test
    @DisplayName("마일스톤 생성 실패 - 프로젝트가 존재하지 않음")
    void createMileStone_ThrowsNotFoundException() {
        MileStoneCreateRequest request = new MileStoneCreateRequest("Sprint 1", 999L);
        given(mileStoneRepository.existsByName("Sprint 1")).willReturn(false);
        given(projectRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> mileStoneService.createMileStone(request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("마일스톤 단건 조회 성공")
    void getMileStone_Success() {
        MileStone mileStone = MileStone.builder().id(1L).name("Sprint 1").build();
        given(mileStoneRepository.findById(1L)).willReturn(Optional.of(mileStone));

        MileStoneDto result = mileStoneService.getMileStone(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Sprint 1");
    }

    @Test
    @DisplayName("마일스톤 단건 조회 실패 - 존재하지 않는 마일스톤")
    void getMileStone_ThrowsMileStoneNotFoundException() {
        given(mileStoneRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> mileStoneService.getMileStone(1L))
                .isInstanceOf(MileStoneNotFoundException.class);
    }

    @Test
    @DisplayName("마일스톤 수정 성공")
    void updateMileStone_Success() {
        MileStone mileStone = MileStone.builder().id(1L).name("Old Name").build();
        MileStoneUpdateRequest request = new MileStoneUpdateRequest("New Name");

        given(mileStoneRepository.findById(1L)).willReturn(Optional.of(mileStone));
        given(mileStoneRepository.existsByName("New Name")).willReturn(false);

        MileStoneDto result = mileStoneService.updateMileStone(1L, request);

        assertThat(result.getName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("마일스톤 삭제 성공")
    void deleteMileStone_Success() {
        given(mileStoneRepository.existsById(1L)).willReturn(true);

        mileStoneService.deleteMileStone(1L);

        verify(mileStoneRepository).deleteById(1L);
    }
}