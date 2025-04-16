package com.cs.calendarback.board.controller;

import com.cs.calendarback.board.dto.BoardCreateRequest;
import com.cs.calendarback.board.dto.BoardResponse;
import com.cs.calendarback.board.dto.BoardUpdateRequest;
import com.cs.calendarback.board.entity.Board;
import com.cs.calendarback.board.service.BoardService;
import com.cs.calendarback.board.dto.BoardPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping()
    public ResponseEntity<BoardResponse> create(@RequestBody BoardCreateRequest boardCreateRequest) {
        Board board = boardService.create(boardCreateRequest);
        return ResponseEntity.ok(BoardResponse.from(board));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> update(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateRequest boardUpdateRequest) {
        Board board = boardService.update(boardId, boardUpdateRequest);
        return ResponseEntity.ok(BoardResponse.from(board));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> read(@PathVariable("boardId") Long boardId) {
        Board board = boardService.read(boardId);
        return ResponseEntity.ok(BoardResponse.from(board));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<BoardPageResponse> readAll(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        List<BoardResponse> boardResponses = boardService.readAll(page, pageSize).stream().map(BoardResponse::from).toList();
        Long count = boardService.count(page, pageSize);
        return ResponseEntity.ok(BoardPageResponse.from(boardResponses, count));
    }
}
