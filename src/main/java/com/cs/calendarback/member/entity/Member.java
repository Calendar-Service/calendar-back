package com.cs.calendarback.member.entity;

import com.cs.calendarback.board.entity.Board;
import com.cs.calendarback.calendar.entity.Category;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private Long authId;

    private String email;

    @OneToMany(mappedBy = "member")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy="member")
    private List<Board> boards = new ArrayList<>();

    public Member(Long id, String nickName, Long authId, String email) {
        this.id = id;
        this.nickName = nickName;
        this.authId = authId;
        this.email = email;
    }

    public static Member create(String nickName, Long authId, String email ) {
        return new Member(null, nickName, authId, email);
    }
    public static Member update(Long id, String nickName, Long authId, String email ) {
        return new Member(id, nickName, authId, email);
    }
}
