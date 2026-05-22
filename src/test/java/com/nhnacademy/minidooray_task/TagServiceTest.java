package com.nhnacademy.minidooray_task;

import com.nhnacademy.minidooray_task.dto.TagCreateRequest;
import com.nhnacademy.minidooray_task.dto.TagDto;
import com.nhnacademy.minidooray_task.entity.Project;
import com.nhnacademy.minidooray_task.entity.Tag;
import com.nhnacademy.minidooray_task.exception.NotFoundException;
import com.nhnacademy.minidooray_task.exception.TagAlreadyExistsException;
import com.nhnacademy.minidooray_task.exception.TagNotFoundException;
import com.nhnacademy.minidooray_task.repository.ProjectRepository;
import com.nhnacademy.minidooray_task.repository.TagRepository;
import com.nhnacademy.minidooray_task.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    @DisplayName("태그 생성 성공")
    void createTag_Success() {
        TagCreateRequest request = new TagCreateRequest("Bug", 1L);
        Project project = Project.builder().build();
        Tag savedTag = Tag.builder().id(10L).name("Bug").project(project).build();

        given(tagRepository.existsByName("Bug")).willReturn(false);
        given(projectRepository.findById(1L)).willReturn(Optional.of(project));
        given(tagRepository.save(any(Tag.class))).willReturn(savedTag);

        TagDto result = tagService.createTag(request);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("Bug");
    }

    @Test
    @DisplayName("태그 생성 실패 - 이미 존재하는 태그 이름")
    void createTag_ThrowsTagAlreadyExistsException() {
        TagCreateRequest request = new TagCreateRequest("Duplicate", 1L);
        given(tagRepository.existsByName("Duplicate")).willReturn(true);

        assertThatThrownBy(() -> tagService.createTag(request))
                .isInstanceOf(TagAlreadyExistsException.class);
    }

    @Test
    @DisplayName("태그 생성 실패 - 프로젝트가 존재하지 않음")
    void createTag_ThrowsNotFoundException() {
        TagCreateRequest request = new TagCreateRequest("Bug", 999L);
        given(tagRepository.existsByName("Bug")).willReturn(false);
        given(projectRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.createTag(request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("태그 단건 조회 성공")
    void getTag_Success() {
        Tag tag = Tag.builder().id(1L).name("Bug").build();
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));

        TagDto result = tagService.getTag(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Bug");
    }

    @Test
    @DisplayName("태그 단건 조회 실패 - 존재하지 않는 태그")
    void getTag_ThrowsTagNotFoundException() {
        given(tagRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.getTag(1L))
                .isInstanceOf(TagNotFoundException.class);
    }

    @Test
    @DisplayName("전체 태그 목록 조회 성공")
    void getAllTags_Success() {
        List<Tag> tags = List.of(
                Tag.builder().id(1L).name("Bug").build(),
                Tag.builder().id(2L).name("Feature").build()
        );
        given(tagRepository.findAll()).willReturn(tags);

        List<TagDto> result = tagService.getAllTags();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Bug");
    }

    @Test
    @DisplayName("태그 삭제 성공")
    void deleteTag_Success() {
        given(tagRepository.existsById(1L)).willReturn(true);

        tagService.deleteTag(1L);

        verify(tagRepository).deleteById(1L);
    }

    @Test
    @DisplayName("태그 삭제 실패 - 존재하지 않는 태그")
    void deleteTag_ThrowsTagNotFoundException() {
        given(tagRepository.existsById(1L)).willReturn(false);

        assertThatThrownBy(() -> tagService.deleteTag(1L))
                .isInstanceOf(TagNotFoundException.class);
    }
}