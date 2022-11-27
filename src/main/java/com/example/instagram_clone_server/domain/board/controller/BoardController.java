package com.example.instagram_clone_server.domain.board.controller;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.service.BoardService;
import com.example.instagram_clone_server.domain.image.model.Image;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "boards", description = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "post boards")
    @PostMapping
    public BoardResponse saveBoard(BoardRequest boardRequest) throws IOException {
        return boardService.saveBoard(boardRequest);
    }

    @Operation(summary = "get boards")
    @GetMapping
    public List<BoardResponse> getBoards(@RequestParam Long lastId, @RequestParam int size) {
        return boardService.getBoards(lastId, size);
    }

    @Operation(summary = "get board")
    @GetMapping("/{boardId}")
    public BoardResponse getBoard(@PathVariable Long boardId) {
        return boardService.getBoard(boardId);
    }

    @Operation(summary = "update board")
    @PutMapping("/{boardId}")
    public BoardResponse updateBoard(@PathVariable Long boardId, BoardRequest boardRequest) throws IOException {
        return boardService.updateBoard(boardId, boardRequest);
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
        private List<String> images;

        public static BoardResponse of(Board board) {
            return BoardResponse.builder()
                    .id(board.getBoardId())
                    .content(board.getContent())
                    .images(board.getImages().stream().map((Image::getImageUrl)).collect(Collectors.toList()))
                    .build();
        }
    }

}

