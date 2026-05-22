package com.nhnacademy.minidooray_task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;
}
