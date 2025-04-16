package com.cs.calendarback.board.controller;

import com.cs.calendarback.board.dto.BoardCreateRequest;
import com.cs.calendarback.board.dto.BoardResponse;
import com.cs.calendarback.board.dto.BoardUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class BoardControllerTest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void create() {
        BoardResponse boardResponse = create(new BoardCreateRequest(
                "hi", "my content", 1L

        ));
        System.out.println("boardResponse" + boardResponse);
    }

    BoardResponse create(BoardCreateRequest request) {
        return restClient.post()
                .uri("/api/v1/boards")
                .body(request)
                .retrieve()
                .body(BoardResponse.class);
    }

    @Test
    void read() {
        BoardResponse boardResponse = read(2L);
        System.out.println(boardResponse);
    }

    BoardResponse read(Long boardId) {
        return restClient.get()
                .uri("/api/v1/boards/{boardId}", boardId)
                .retrieve()
                .body(BoardResponse.class);
    }


    @Test
    void update() {
        BoardResponse boardResponse = update(new BoardUpdateRequest(
                "hi udpate", "content update", 1L
        ));

    }

    BoardResponse update(BoardUpdateRequest request) {
        return restClient.put()
                .uri("/api/v1/boards/{boardId}", 2L)
                .body(request)
                .retrieve()
                .body(BoardResponse.class);
    }

    @Test
    void delete() {
        BoardResponse boardResponse = delete(2L);
    }

    BoardResponse delete(Long boardId) {
        return restClient.delete()
                .uri("/api/v1/boards/{boardId}", boardId)
                .retrieve()
                .body(BoardResponse.class);
    }
}