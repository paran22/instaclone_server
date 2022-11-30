package com.example.instagram_clone_server.domain.comment.controller;

import com.example.instagram_clone_server.common.ApiResponse;
import com.example.instagram_clone_server.domain.comment.model.Comment;
import com.example.instagram_clone_server.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@Tag(name = "comments", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "post comment")
    @PostMapping("/{boardId}/comments")
    public ApiResponse<CommentResponse> saveComment(@PathVariable Long boardId, @RequestBody CommentRequest commentRequest) {
        return ApiResponse.success(commentService.saveComment(boardId, commentRequest));
    }

    @Operation(summary = "delete comment")
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ApiResponse<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentRequest {
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentResponse {
        private Long id;
        private String content;

        public static CommentController.CommentResponse of(Comment comment) {
            return CommentResponse.builder()
                    .id(comment.getCommentId())
                    .content(comment.getContent())
                    .build();
        }
    }
}
