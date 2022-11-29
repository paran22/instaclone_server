package com.example.instagram_clone_server.domain.comment.service;

import com.example.instagram_clone_server.domain.board.model.Board;
import com.example.instagram_clone_server.domain.board.repository.BoardRepository;
import com.example.instagram_clone_server.domain.board.service.BoardService;
import com.example.instagram_clone_server.domain.comment.controller.CommentController;
import com.example.instagram_clone_server.domain.comment.controller.CommentController.CommentRequest;
import com.example.instagram_clone_server.domain.comment.model.Comment;
import com.example.instagram_clone_server.domain.comment.model.Comments;
import com.example.instagram_clone_server.domain.comment.repository.CommentRepository;
import com.example.instagram_clone_server.exception.CustomException;
import com.example.instagram_clone_server.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.instagram_clone_server.domain.comment.controller.CommentController.CommentResponse;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentResponse saveComment(Long boardId, CommentRequest commentRequest) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Comment comment = commentRepository.save(Comment.of(commentRequest.getContent(), board));
        return CommentResponse.of(comment);
    }

    public Comments getComments(Board board) {
        return Comments.of(commentRepository.findAllByBoard(board));
    }

}
