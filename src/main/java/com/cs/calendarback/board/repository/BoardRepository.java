package com.cs.calendarback.board.repository;

import com.cs.calendarback.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(
            value = "select board.board_id, board.title, board.content, board.created_at, board.updated_at, board.member_id " +
                    "from (" +
                    "   select board_id " +
                    "   from board " +
                    "   order by created_at desc " +
                    "   limit :limit offset :offset" +
                    ") t left join board on t.board_id = board.board_id"
            , nativeQuery = true
    )
    List<Board> findAll(@Param("limit") Long limit, @Param("offset") Long offset);

    @Query(
            value = "select count(*) " +
                    "from (" +
                    "   select board_id " +
                    "   from board " +
                    "   limit :limit " +
                    ") t"
            , nativeQuery = true
    )
    Long count(@Param("limit") Long limit);
}
