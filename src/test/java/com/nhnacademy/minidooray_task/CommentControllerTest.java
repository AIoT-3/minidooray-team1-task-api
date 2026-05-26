package com.nhnacademy.minidooray_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray_task.dto.CommentDto;
import com.nhnacademy.minidooray_task.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 성공")
    void create_success() throws Exception {
        // given
        CommentDto.Create request = new CommentDto.Create();
        ReflectionTestUtils.setField(request, "content", "테스트 댓글 내용");

        CommentDto.Response response = new CommentDto.Response();
        ReflectionTestUtils.setField(response, "id", 100L);
        ReflectionTestUtils.setField(response, "content", "테스트 댓글 내용");

        given(commentService.create(eq(1L), eq(2L), any(CommentDto.Create.class), eq(10L)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/projects/{projectId}/tasks/{taskId}/comments", 1L, 2L)
                        .param("memberId", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.content").value("테스트 댓글 내용"));
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void update_success() throws Exception {
        // given
        CommentDto.Update request = new CommentDto.Update();
        ReflectionTestUtils.setField(request, "content", "수정된 댓글 내용");

        CommentDto.Response response = new CommentDto.Response();
        ReflectionTestUtils.setField(response, "id", 100L);
        ReflectionTestUtils.setField(response, "content", "수정된 댓글 내용");

        given(commentService.update(eq(1L), eq(2L), eq(100L), any(CommentDto.Update.class), eq(10L)))
                .willReturn(response);

        // when & then
        mockMvc.perform(put("/projects/{projectId}/tasks/{taskId}/comments/{commentId}", 1L, 2L, 100L)
                        .param("memberId", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.content").value("수정된 댓글 내용"));
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_success() throws Exception {
        // given
        willDoNothing().given(commentService).delete(1L, 2L, 100L, 10L);

        // when & then
        mockMvc.perform(delete("/projects/{projectId}/tasks/{taskId}/comments/{commentId}", 1L, 2L, 100L)
                        .param("memberId", "10"))
                .andExpect(status().isNoContent());
    }
}
