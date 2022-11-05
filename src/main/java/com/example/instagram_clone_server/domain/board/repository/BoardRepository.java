package com.example.instagram_clone_server.domain.board.repository;

import com.example.instagram_clone_server.domain.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
