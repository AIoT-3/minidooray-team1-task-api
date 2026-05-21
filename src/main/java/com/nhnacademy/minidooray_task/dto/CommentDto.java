package com.nhnacademy.minidooray_task.dto;

import com.nhnacademy.minidooray_task.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    public static class Create {
        private String content;
    }

    @Getter
    @NoArgsConstructor
    public static class Update {
        private String content;
    }

    @Getter
    public static class Response {
        private Long id;
        private String content;
        private Long memberId;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;

        public static Response from(Comment comment) {
            Response response = new Response();
            response.id = comment.getId();
            response.content = comment.getContent();
//            response.memberId = comment.getProjectMember().getId();
            response.createdAt = comment.getCreatedAt();
            response.updatedAt = comment.getUpdatedAt();
            return response;
        }

    }
}

