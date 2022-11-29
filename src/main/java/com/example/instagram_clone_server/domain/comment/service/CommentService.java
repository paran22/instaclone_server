package com.example.instagram_clone_server.domain.comment.service;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.service.BoardService;
import com.example.instagram_clone_server.domain.comment.model.Comment;
import com.example.instagram_clone_server.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.instagram_clone_server.domain.comment.controller.CommentController.CommentResponse;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardService boardService;

    public CommentResponse saveComment(Long boardId, String content) {
        Board board = boardService.findBoardById(boardId);
        Comment comment = commentRepository.save(Comment.of(content, board));
        return CommentResponse.of(comment);
    }

}
