package com.cs.calendarback.board.entity;

import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.entity.common.BaseEntity;
import com.cs.calendarback.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static Board create(String title, String content, Member member) {
        return new Board(null, title, content, member);
    }

    public void update( String title, String content) {
        this.title = title;
        this.content = content;
    }
}
