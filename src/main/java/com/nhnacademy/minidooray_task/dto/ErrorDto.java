package com.nhnacademy.minidooray_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
}
