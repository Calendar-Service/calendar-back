package com.cs.calendarback.board.dto;

import com.cs.calendarback.board.entity.Board;

public record BoardResponse(Long id) {

    public static BoardResponse from(Board board) {
        return new BoardResponse(board.getId());
    }
}
