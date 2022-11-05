package com.example.instagram_clone_server.domain.image.repository;

import com.example.instagram_clone_server.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
