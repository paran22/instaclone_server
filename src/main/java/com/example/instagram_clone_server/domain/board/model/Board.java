package com.example.instagram_clone_server.domain.board.model;

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
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;

    @Column(nullable = false)
    private String content;


    public static Board of(String content) {
        return Board.builder()
                .content(content)
                .build();
    }
}
