package com.example.instagram_clone_server.domain.comment.model;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.common.model.Timestamped;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
