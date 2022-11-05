package com.example.instagram_clone_server.domain.image.model;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.common.model.Timestamped;
import com.example.instagram_clone_server.domain.image.service.S3Uploader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;

import static com.example.instagram_clone_server.domain.image.service.S3Uploader.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public static Image of(HashMap<String, String> image, Board board) {
        return Image.builder()
                .board(board)
                .imageName(image.get(IMAGE_NAME))
                .imageUrl(image.get(IMAGE_URL))
                .build();
    }
}
