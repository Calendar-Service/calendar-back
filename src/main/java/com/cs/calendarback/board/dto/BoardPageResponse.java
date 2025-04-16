package com.cs.calendarback.board.dto;

import java.util.List;

public record BoardPageResponse (List<BoardResponse> boards, Long count){
    public static BoardPageResponse from(List<BoardResponse> boards, Long count) {
        return new BoardPageResponse(boards, count);
    }
}
