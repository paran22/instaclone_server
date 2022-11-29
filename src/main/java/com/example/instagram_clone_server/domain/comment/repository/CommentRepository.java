package com.example.instagram_clone_server.domain.comment.repository;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard(Board board);
    void deleteAllByBoard(Board board);
}
