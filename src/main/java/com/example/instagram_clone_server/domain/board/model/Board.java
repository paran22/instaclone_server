package com.example.instagram_clone_server.domain.board.model;

import com.example.instagram_clone_server.domain.common.model.Timestamped;
import com.example.instagram_clone_server.domain.image.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();

    public static Board of(String content) {
        return Board.builder()
                .content(content)
                .images(new ArrayList<>())
                .build();
    }


    public void addImage(Image image) {
        this.images.add(image);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
