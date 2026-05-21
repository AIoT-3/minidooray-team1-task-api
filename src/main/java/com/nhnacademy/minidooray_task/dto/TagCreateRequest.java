package com.nhnacademy.minidooray_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagCreateRequest {
    private String name;
    private Long projectId;
}
