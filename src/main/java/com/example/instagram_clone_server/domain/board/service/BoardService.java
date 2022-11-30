package com.example.instagram_clone_server.domain.board.service;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.repository.BoardRepository;
import com.example.instagram_clone_server.domain.comment.model.Comments;
import com.example.instagram_clone_server.domain.comment.service.CommentService;
import com.example.instagram_clone_server.domain.image.model.Image;
import com.example.instagram_clone_server.domain.image.model.Images;
import com.example.instagram_clone_server.domain.image.service.ImageService;
import com.example.instagram_clone_server.exception.CustomException;
import com.example.instagram_clone_server.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.instagram_clone_server.domain.board.controller.BoardController.BoardRequest;
import static com.example.instagram_clone_server.domain.board.controller.BoardController.BoardResponse;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageService imageService;
    private final CommentService commentService;

    public BoardResponse saveBoard(BoardRequest boardRequest) throws IOException {
        String content = boardRequest.getContent();
        Board savedBoard = boardRepository.save(Board.of(content));

        List<MultipartFile> imageFiles = boardRequest.getImages();
        Images images = imageService.saveImages(imageFiles, savedBoard);

        return BoardResponse.of(savedBoard, images);
    }

    public List<BoardResponse> getBoards(Long lastId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Board> boards = boardRepository.findByBoardIdGreaterThanEqualOrderByCreatedAt(lastId, pageRequest);
        return boards.stream().map((board -> {
            Images images = imageService.getImages(board);
            Comments comments = commentService.getComments(board);
            return BoardResponse.of(board, images, comments);
        })).collect(Collectors.toList());
    }

    public BoardResponse getBoard(Long boardId) {
        Board board = findBoardById(boardId);
        Images images = imageService.getImages(board);
        Comments comments = commentService.getComments(board);
        return BoardResponse.of(board, images, comments);
    }

    @Transactional
    public BoardResponse updateBoard(Long boardId, BoardRequest boardRequest) throws IOException {
        Board board = findBoardById(boardId);
        board.updateContent(boardRequest.getContent());
        imageService.deleteImages(board);
        Images images = imageService.saveImages(boardRequest.getImages(), board);
        return BoardResponse.of(board, images);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = findBoardById(boardId);
        imageService.deleteImages(board);
        boardRepository.delete(board);
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }
}
