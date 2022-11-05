package com.example.instagram_clone_server.domain.board.controller;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.service.BoardService;
import com.example.instagram_clone_server.domain.image.model.Image;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public BoardResponse saveBoard(BoardRequest boardRequest) throws IOException {
        return boardService.saveBoard(boardRequest);
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
        private String content;
        private List<String> images;

        public static BoardResponse of(Board board, List<Image> images) {
            return BoardResponse.builder()
                    .content(board.getContent())
                    .images(images.stream().map((Image::getImageUrl)).collect(Collectors.toList()))
                    .build();
        }
    }

}

