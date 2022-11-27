package com.example.instagram_clone_server.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    public static final String IMAGE_NAME = "imageName";
    public static final String IMAGE_URL = "imageUrl";

    @Value("${aws.s3.image.bucket}")
    public String bucket;

    public HashMap<String, String> upload(MultipartFile multipartFile, String dirName) throws IOException {

        isImage(multipartFile);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        String fileName = createFileName(multipartFile);
        String uploadImageName = dirName + "/" + UUID.randomUUID() + fileName;

        amazonS3Client.putObject(new PutObjectRequest(bucket, uploadImageName, multipartFile.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        String uploadImageUrl = amazonS3Client.getUrl(bucket, uploadImageName).toString();


        HashMap<String, String> imgInfo = new HashMap<>();
        imgInfo.put(IMAGE_NAME, uploadImageName);
        imgInfo.put(IMAGE_URL, uploadImageUrl);
        return imgInfo;
    }

    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }


    private String createFileName(MultipartFile multipartFile) {

        String name = multipartFile.getOriginalFilename();
        String ext = "";

        if (name != null && name.contains(".")) {
            int position = name.lastIndexOf(".");
            ext = name.substring(position + 1);
            name = name.substring(0, position);
        }

        return !ext.equals("") ? name + "." + ext : name;
    }


    private void isImage(MultipartFile multipartFile) throws IOException {

        Tika tika = new Tika();
        String mimeType = tika.detect(multipartFile.getInputStream());

        if (!mimeType.startsWith("image/")) {
            throw new IllegalArgumentException("업로드하려는 파일이 이미지 파일이 아닙니다.");
        }
    }
}
