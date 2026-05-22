package com.nhnacademy.minidooray_task.dto;


import com.nhnacademy.minidooray_task.entity.Task;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

public class TaskDto {
    @Getter
    @NoArgsConstructor
    public static class Create {
        @NotBlank(message = "제목은 필수입니다")
        private String title;
        private String content;
        private Long milestone;
    }

    @Getter
    @NoArgsConstructor
    public static class Update {
        private String title;
        private String content;
        private Long milestone;
    }


    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private Long milestoneId;
        private Long memberId;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;

        public static Response from(Task task) {
            Response response = new Response();
            response.id = task.getId();
            response.title = task.getTitle();
            response.content = task.getContent();
            response.milestoneId = task.getMilestone() != null ? task.getMilestone().getId() : null;
//            response.memberId = task.getProjectMember().getId();
            response.createdAt = task.getCreatedAt();
            response.updatedAt = task.getUpdatedAt();
            return response;
        }
    }

}
