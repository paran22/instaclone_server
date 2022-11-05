package com.example.instagram_clone_server.domain.comment.repository;

import com.example.instagram_clone_server.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
