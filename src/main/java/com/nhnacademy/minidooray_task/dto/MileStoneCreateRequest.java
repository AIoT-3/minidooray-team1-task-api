package com.nhnacademy.minidooray_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MileStoneCreateRequest {
    private String name;
    private Long projectId;
}
