package com.example.instagram_clone_server.domain.image.service;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.image.model.Image;
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

    public void saveImages(List<MultipartFile> imageFiles, Board board) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            HashMap<String, String> convertedImg = s3Uploader.upload(imageFile, dirName);
            Image image = Image.of(convertedImg, board);
            images.add(image);
            board.addImage(image);
        }
        imageRepository.saveAll(images);
    }

    @Transactional
    public void deleteImages(List<Image> images) {
        for (Image image : images) {
            s3Uploader.deleteFile(image.getImageName());
        }
        imageRepository.deleteAll(images);
    }
}
