package com.cs.calendarback.calendar.repository;


import com.cs.calendarback.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s " +
            "FROM Schedule s " +
            "LEFT JOIN FETCH s.member m " +
            "LEFT JOIN FETCH s.category c " +
            "WHERE s.startDateTime >= :startDateTime " +
            "AND s.endDateTime <= :endDateTime " +
            "AND s.member.id = :memberId ")
    List<Schedule> findSchedulesByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Long memberId);

    @Query("SELECT s " +
            "FROM Schedule s " +
            "LEFT JOIN FETCH s.member m " +
            "LEFT JOIN FETCH s.category c " +
            "WHERE s.startDateTime >= :startDateTime " +
            "AND s.endDateTime <= :endDateTime " +
            "AND s.member.id = :memberId " +
            "AND s.category.id = :categoryId")
    List<Schedule> findSchedulesByDateRangeAndCategoryId(LocalDateTime startDateTime, LocalDateTime endDateTime, Long memberId, Long categoryId);

    @Query("SELECT s FROM Schedule s " +
            "LEFT JOIN FETCH s.member m " +
            "LEFT JOIN FETCH s.category c " +
            "WHERE m.id = :memberId")
    List<Schedule> findByMemberId(Long memberId);

    @Query("SELECT s FROM Schedule s " +
            "LEFT JOIN FETCH s.member m " +
            "LEFT JOIN FETCH s.category c " +
            "WHERE m.id = :memberId " +
            "AND c.id = :categoryId")
    List<Schedule> findByMemberIdAndCategoryId(Long memberId, Long categoryId);

}
