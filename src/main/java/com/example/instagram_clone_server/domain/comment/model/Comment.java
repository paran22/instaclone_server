package com.example.instagram_clone_server.domain.comment.model;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.comment.controller.CommentController;
import com.example.instagram_clone_server.domain.common.model.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public static Comment of(String content, Board board) {
        return Comment.builder()
                .content(content)
                .board(board)
                .build();
    }
}
