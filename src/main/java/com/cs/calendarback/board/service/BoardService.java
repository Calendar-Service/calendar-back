package com.cs.calendarback.board.service;

import com.cs.calendarback.board.dto.BoardCreateRequest;
import com.cs.calendarback.board.dto.BoardUpdateRequest;
import com.cs.calendarback.board.entity.Board;
import com.cs.calendarback.board.repository.BoardRepository;
import com.cs.calendarback.board.util.PageLimitCalculator;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import com.cs.calendarback.member.entity.Member;
import com.cs.calendarback.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Board create(BoardCreateRequest request) {
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new CoreException(ErrorType.MEMBER_NOT_FOUND, request.memberId()));
        return boardRepository.save(Board.create(request.title(), request.content(), member));
    }

    @Transactional
    public Board update(Long boardId, BoardUpdateRequest request) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CoreException(ErrorType.MEMBER_NOT_FOUND, boardId));
        board.update(request.title(), request.content());
        return board;
    }

    public Board read(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new CoreException(ErrorType.BOARD_NOT_FOUND, boardId));
    }

    @Transactional
    public void delete(Long boardId) {
        boardRepository.delete(boardRepository.findById(boardId).orElseThrow(() -> new CoreException(ErrorType.BOARD_NOT_FOUND, boardId)));
    }

    public List<Board> readAll(Long page, Long pageSize) {
        return boardRepository.findAll(pageSize, (page - 1) * pageSize);
    }

    public Long count(Long page, Long pageSize) {
        return boardRepository.count(PageLimitCalculator.calculatePageLimit(page, pageSize, 10L));
    }
}
