package com.nhnacademy.minidooray_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray_task.dto.TaskDto;
import com.nhnacademy.minidooray_task.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    @DisplayName("Task 생성 성공")
    void create_success() throws Exception {
        // given
        TaskDto.Create request = new TaskDto.Create();
        ReflectionTestUtils.setField(request, "title", "새로운 태스크");
        ReflectionTestUtils.setField(request, "content", "태스크 내용");

        TaskDto.Response response = new TaskDto.Response();
        ReflectionTestUtils.setField(response, "id", 1L);
        ReflectionTestUtils.setField(response, "title", "새로운 태스크");

        given(taskService.create(eq(1L), any(TaskDto.Create.class), eq(10L)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/projects/{projectId}/tasks", 1L, 0L)
                        .param("memberId", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("새로운 태스크"));
    }

    @Test
    @DisplayName("Task 목록 조회 성공")
    void list_success() throws Exception {
        // given
        TaskDto.Response res1 = new TaskDto.Response();
        ReflectionTestUtils.setField(res1, "id", 1L);
        TaskDto.Response res2 = new TaskDto.Response();
        ReflectionTestUtils.setField(res2, "id", 2L);

        given(taskService.list(1L, 10L)).willReturn(List.of(res1, res2));

        // when & then
        mockMvc.perform(get("/projects/{projectId}/tasks", 1L, 0L)
                        .param("memberId", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    @DisplayName("Task 상세 조회 성공")
    void content_success() throws Exception {
        // given
        TaskDto.Response response = new TaskDto.Response();
        ReflectionTestUtils.setField(response, "id", 2L);
        ReflectionTestUtils.setField(response, "title", "조회된 태스크");

        given(taskService.content(1L, 2L, 10L)).willReturn(response);

        // when & then
        mockMvc.perform(get("/projects/{projectId}/tasks/{taskId}", 1L, 2L, 2L)
                        .param("memberId", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("조회된 태스크"));
    }

    @Test
    @DisplayName("Task 수정 성공")
    void update_success() throws Exception {
        // given
        TaskDto.Update request = new TaskDto.Update();
        ReflectionTestUtils.setField(request, "title", "수정된 제목");

        TaskDto.Response response = new TaskDto.Response();
        ReflectionTestUtils.setField(response, "id", 2L);
        ReflectionTestUtils.setField(response, "title", "수정된 제목");

        // eq(2L) 자리에 메서드 패스로 바인딩되는 taskId 값을 정확히 맞추어 줍니다.
        given(taskService.update(eq(1L), eq(2L), any(TaskDto.Update.class), eq(10L)))
                .willReturn(response);

        // when & then
        // URL 매핑 인자값을 1L, 2L, 2L 로 맞춰 요청을 보냅니다.
        mockMvc.perform(put("/projects/{projectId}/tasks/{taskId}", 1L, 2L, 2L)
                        .param("memberId", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("수정된 제목"));
    }

    @Test
    @DisplayName("Task 삭제 성공")
    void delete_success() throws Exception {
        // given
        willDoNothing().given(taskService).delete(1L, 2L, 10L);

        // when & then
        mockMvc.perform(delete("/projects/{projectId}/tasks/{taskId}", 1L, 0L, 2L)
                        .param("memberId", "10"))
                .andExpect(status().isNoContent());
    }
}