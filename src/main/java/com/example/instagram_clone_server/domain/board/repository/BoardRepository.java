package com.example.instagram_clone_server.domain.board.repository;

import com.example.instagram_clone_server.domain.board.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByBoardIdGreaterThanEqualOrderByCreatedAt(Long lastId, Pageable pageable);
}
