package com.example.instagram_clone_server.domain.board.model;

import com.example.instagram_clone_server.domain.common.model.Timestamped;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;

    @Column(nullable = false)
    private String content;

}
