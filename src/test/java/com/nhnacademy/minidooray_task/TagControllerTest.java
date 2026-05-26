package com.nhnacademy.minidooray_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray_task.controller.TagController;
import com.nhnacademy.minidooray_task.dto.TagCreateRequest;
import com.nhnacademy.minidooray_task.dto.TagDto;
import com.nhnacademy.minidooray_task.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TagService tagService;

    @Test
    @DisplayName("태그 생성 성공 (201)")
    void createTag_Success() throws Exception {
        TagCreateRequest request = new TagCreateRequest("Bug", 1L);
        TagDto responseDto = new TagDto(1L, "Bug");

        given(tagService.createTag(any(TagCreateRequest.class))).willReturn(responseDto);

        mockMvc.perform(post("/projects/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Bug"));
    }

    @Test
    @DisplayName("태그 단건 조회 성공 (200)")
    void getTag_Success() throws Exception {
        TagDto responseDto = new TagDto(1L, "Bug");
        given(tagService.getTag(1L)).willReturn(responseDto);

        mockMvc.perform(get("/projects/1/tags/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Bug"));
    }

    @Test
    @DisplayName("이름이 공백일 때 생성 실패 - MethodArgumentNotValidException 발생 검증")
    void createTag_ValidationFailure() throws Exception {
        TagCreateRequest invalidRequest = new TagCreateRequest("", 1L);

        mockMvc.perform(post("/projects/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(org.springframework.web.bind.MethodArgumentNotValidException.class));
    }
}