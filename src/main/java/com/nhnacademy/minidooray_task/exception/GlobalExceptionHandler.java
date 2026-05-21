package com.nhnacademy.minidooray_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nhnacademy.minidooray_task.dto.ErrorDto;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTagNotFoundException(TagNotFoundException ex) {
        ErrorDto response = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleTagAlreadyExistsException(TagAlreadyExistsException ex) {
        ErrorDto response = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        ErrorDto response = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MileStoneNotFoundException.class)
    public ResponseEntity<ErrorDto> handleMileStoneNotFoundException(MileStoneNotFoundException ex) {
        ErrorDto response = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MileStoneAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleMileStoneAlreadyExistsException(MileStoneAlreadyExistsException ex) {
        ErrorDto response = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ErrorDto response = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidProjectMemberException.class)
    public ResponseEntity<ErrorDto> handleInvalidProjectMemberException(InvalidProjectMemberException ex) {
        ErrorDto response = new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
