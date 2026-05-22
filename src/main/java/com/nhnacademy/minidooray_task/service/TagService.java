package com.nhnacademy.minidooray_task.service;

import com.nhnacademy.minidooray_task.dto.TagCreateRequest;
import com.nhnacademy.minidooray_task.dto.TagDto;
import com.nhnacademy.minidooray_task.entity.Tag;
import com.nhnacademy.minidooray_task.exception.TagAlreadyExistsException;
import com.nhnacademy.minidooray_task.exception.TagNotFoundException;
import com.nhnacademy.minidooray_task.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public TagDto createTag(TagCreateRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new TagAlreadyExistsException(request.getName());
        }
        Tag tag = new Tag(request.getName());
        Tag savedTag = tagRepository.save(tag);

        return new TagDto(savedTag.getId(), savedTag.getName());
    }

    public TagDto getTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));

        return new TagDto(tag.getId(), tag.getName());
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> new TagDto(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }
     @Transactional
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new TagNotFoundException(id);
        }
         tagRepository.deleteById(id);
     }
}
