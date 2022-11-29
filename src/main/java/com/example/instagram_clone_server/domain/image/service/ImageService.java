package com.example.instagram_clone_server.domain.image.service;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.image.model.Image;
import com.example.instagram_clone_server.domain.image.model.Images;
import com.example.instagram_clone_server.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;
    private static final String dirName = "board";

    public Images saveImages(List<MultipartFile> imageFiles, Board board) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            HashMap<String, String> convertedImg = s3Uploader.upload(imageFile, dirName);
            Image image = Image.of(convertedImg, board);
            images.add(image);
        }
        return Images.of(imageRepository.saveAll(images));
    }

    public Images getImages(Board board) {
        return Images.of(imageRepository.findAllByBoard(board));
    }

    @Transactional
    public void deleteImages(Board board) {
        Images images = getImages(board);
        for (Image image : images.getImages()) {
            s3Uploader.deleteFile(image.getImageName());
        }
        imageRepository.deleteAllByBoard(board);
    }
}
