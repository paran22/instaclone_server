package com.example.instagram_clone_server.domain.board.service;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.repository.BoardRepository;
import com.example.instagram_clone_server.domain.image.model.Image;
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

    public BoardResponse saveBoard(BoardRequest boardRequest) throws IOException {
        String content = boardRequest.getContent();
        Board savedBoard = boardRepository.save(Board.of(content));

        List<MultipartFile> imageFiles = boardRequest.getImages();
        imageService.saveImages(imageFiles, savedBoard);

        return BoardResponse.of(savedBoard);
    }

    public List<BoardResponse> getBoards(Long lastId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Board> boards = boardRepository.findByBoardIdGreaterThanEqualOrderByCreatedAt(lastId, pageRequest);
        return boards.stream().map(BoardResponse::of).collect(Collectors.toList());
    }

    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        return BoardResponse.of(board);
    }

    @Transactional
    public BoardResponse updateBoard(Long boardId, BoardRequest boardRequest) throws IOException {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        board.updateContent(boardRequest.getContent());
        removeAndUpdateImages(board, boardRequest.getImages());

        return BoardResponse.of(board);
    }

    private void removeAndUpdateImages(Board board, List<MultipartFile> images) throws IOException {
        List<Image> savedImages = board.getImages();
        imageService.deleteImages(savedImages);
        board.getImages().removeAll(savedImages);

        imageService.saveImages(images, board);
    }

    @Transactional
    public Long deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        imageService.deleteImages(board.getImages());
        boardRepository.delete(board);
        return board.getBoardId();
    }
}
