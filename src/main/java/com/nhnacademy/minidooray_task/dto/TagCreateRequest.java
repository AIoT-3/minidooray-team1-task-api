package com.nhnacademy.minidooray_task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private Long projectId;
}
