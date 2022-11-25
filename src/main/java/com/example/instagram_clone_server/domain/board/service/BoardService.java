package com.example.instagram_clone_server.domain.board.service;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.repository.BoardRepository;
import com.example.instagram_clone_server.domain.image.model.Image;
import com.example.instagram_clone_server.domain.image.repository.ImageRepository;
import com.example.instagram_clone_server.domain.image.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.instagram_clone_server.domain.board.controller.BoardController.BoardRequest;
import static com.example.instagram_clone_server.domain.board.controller.BoardController.BoardResponse;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;
    private static final String dirName = "board";


    public BoardResponse saveBoard(BoardRequest boardRequest) throws IOException {
        String content = boardRequest.getContent();
        Board savedBoard = boardRepository.save(Board.of(content));

        List<MultipartFile> imageFiles = boardRequest.getImages();

        List<Image> images = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            HashMap<String, String> convertedImg = s3Uploader.upload(imageFile, dirName);
            Image image = Image.of(convertedImg, savedBoard);
            images.add(image);
            List<Image> imageList = savedBoard.getImages();
            imageList.add(image);
        }

        List<Image> savedImages = imageRepository.saveAll(images);

        return BoardResponse.of(savedBoard, savedImages);
    }

    public List<BoardResponse> getBoards(Long lastId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Board> boards = boardRepository.findByBoardIdLessThanEqualOrderByCreatedAt(lastId, pageRequest);
        return boards.stream().map((board) -> BoardResponse.of(board, board.getImages())).collect(Collectors.toList());
    }
}
