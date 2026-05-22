package com.nhnacademy.minidooray_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray_task.controller.MileStoneController;
import com.nhnacademy.minidooray_task.dto.MileStoneCreateRequest;
import com.nhnacademy.minidooray_task.dto.MileStoneDto;
import com.nhnacademy.minidooray_task.service.MileStoneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MileStoneController.class)
class MileStoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MileStoneService mileStoneService;

    @Test
    @DisplayName("마일스톤 생성 성공 (201)")
    void createMileStone_Success() throws Exception {
        MileStoneCreateRequest request = new MileStoneCreateRequest("Iteration 1", 1L);
        MileStoneDto responseDto = new MileStoneDto(1L, "Iteration 1");

        given(mileStoneService.createMileStone(any(MileStoneCreateRequest.class))).willReturn(responseDto);

        mockMvc.perform(post("/api/projects/1/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Iteration 1"));
    }

    @Test
    @DisplayName("마일스톤 단건 조회 성공 (200)")
    void getMileStone_Success() throws Exception {
        MileStoneDto responseDto = new MileStoneDto(1L, "Iteration 1");
        given(mileStoneService.getMileStone(1L)).willReturn(responseDto);

        mockMvc.perform(get("/api/projects/1/milestones/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Iteration 1"));
    }

    @Test
    @DisplayName("이름이 공백일 때 생성 실패 (400) - @Valid 작동 검증")
    void createMileStone_ValidationFailure() throws Exception {
        MileStoneCreateRequest invalidRequest = new MileStoneCreateRequest("", 1L);

        mockMvc.perform(post("/api/projects/1/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(org.springframework.web.bind.MethodArgumentNotValidException.class));
    }
}