package com.cs.calendarback.calendar.entity;

import com.cs.calendarback.calendar.entity.common.BaseEntity;
import com.cs.calendarback.calendar.entity.enums.Author;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Schedule> schedules = new ArrayList<>();

    public static Member create(String name, String email, String password) {
        return new Member(null, name, email, password, new ArrayList<>());
    }
}
