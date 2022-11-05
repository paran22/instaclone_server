package com.example.instagram_clone_server.domain.board.repository;

import com.example.instagram_clone_server.domain.board.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
