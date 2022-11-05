package com.example.instagram_clone_server.domain.board.service;

import com.example.instagram_clone_server.domain.board.controller.BoardController;
import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.repository.BoardRepository;
import com.example.instagram_clone_server.domain.image.model.Image;
import com.example.instagram_clone_server.domain.image.repository.ImageRepository;
import com.example.instagram_clone_server.domain.image.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.instagram_clone_server.domain.board.controller.BoardController.*;

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

        List<HashMap<String, String>> convertedImages = new ArrayList<>();

        List<Image> images = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            HashMap<String, String> image = s3Uploader.upload(imageFile, dirName);
            images.add(Image.of(image, savedBoard));
        }
        List<Image> savedImages = imageRepository.saveAll(images);

        return BoardResponse.of(savedBoard, savedImages);
    }
}
