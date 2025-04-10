package com.cs.calendarback.board.repository;

import com.cs.calendarback.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
