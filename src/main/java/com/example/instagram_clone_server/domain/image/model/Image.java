package com.example.instagram_clone_server.domain.image.model;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.common.model.Timestamped;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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

}
