package com.nhnacademy.minidooray_task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MileStoneUpdateRequest {

    @NotBlank
    private String name;
}