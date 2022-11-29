package com.example.instagram_clone_server.domain.image.repository;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByBoard(Board board);
    void deleteAllByBoard(Board board);
}
