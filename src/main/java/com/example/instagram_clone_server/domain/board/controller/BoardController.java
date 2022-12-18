package com.example.instagram_clone_server.domain.board.controller;

import com.example.instagram_clone_server.common.ApiResponse;
import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.service.BoardService;
import com.example.instagram_clone_server.domain.comment.controller.CommentController;
import com.example.instagram_clone_server.domain.comment.model.Comment;
import com.example.instagram_clone_server.domain.comment.model.Comments;
import com.example.instagram_clone_server.domain.image.model.Image;
import com.example.instagram_clone_server.domain.image.model.Images;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.instagram_clone_server.domain.comment.controller.CommentController.*;

@Tag(name = "boards", description = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "post boards")
    @PostMapping
    public ApiResponse<BoardResponse> saveBoard(BoardRequest boardRequest) throws IOException {
        return ApiResponse.success(boardService.saveBoard(boardRequest));
    }

    @Operation(summary = "get boards")
    @GetMapping
    public ApiResponse<List<BoardResponse>> getBoards(@RequestParam Long lastId, @RequestParam int size) {
        return ApiResponse.success(boardService.getBoards(lastId, size));
    }

    @Operation(summary = "get board")
    @GetMapping("/{boardId}")
    public ApiResponse<BoardResponse> getBoard(@PathVariable Long boardId) {
        return ApiResponse.success(boardService.getBoard(boardId));
    }

    @Operation(summary = "update board")
    @PutMapping("/{boardId}")
    public ApiResponse<BoardResponse> updateBoard(@PathVariable Long boardId, BoardRequest boardRequest) throws IOException {
        return ApiResponse.success(boardService.updateBoard(boardId, boardRequest));
    }

    @Operation(summary = "delete board")
    @DeleteMapping("/{boardId}")
    public ApiResponse<?> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ApiResponse.success();
    }

    @Getter
    @Setter
    public static class BoardRequest {
        private String content;
        private List<MultipartFile> images;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BoardResponse {
        private Long id;
        private String content;
        private List<String> imageUrls;
        private List<CommentResponse> comments;

        public static BoardResponse of(Board board, Images images) {
            return BoardResponse.builder()
                    .id(board.getBoardId())
                    .content(board.getContent())
                    .imageUrls(images.toImageUrls())
                    .comments(new ArrayList<>())
                    .build();
        }

        public static BoardResponse of(Board board, Images images, Comments comments) {
            return BoardResponse.builder()
                    .id(board.getBoardId())
                    .content(board.getContent())
                    .imageUrls(images.toImageUrls())
                    .comments(comments.getComments().stream().map(CommentResponse::of).collect(Collectors.toList()))
                    .build();
        }
    }

}

