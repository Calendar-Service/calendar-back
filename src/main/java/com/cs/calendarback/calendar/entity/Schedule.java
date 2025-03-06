package com.cs.calendarback.calendar.entity;

import com.cs.calendarback.calendar.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "note")
    private String note;

    @Column(name = "startDateTime", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "endDateTime", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public static Schedule create(String title, String note, LocalDateTime startDateTime, LocalDateTime endDateTime, Long userId) {
        return new Schedule(null, title, note, startDateTime, endDateTime, userId);
    }

    public static Schedule update(Long id, String title, String note, LocalDateTime startDateTime, LocalDateTime endDateTime, Long userId) {
        return new Schedule(id, title, note, startDateTime, endDateTime, userId);
    }
}
