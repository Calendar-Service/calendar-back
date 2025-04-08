package com.cs.calendarback.calendar.repository;

import com.cs.calendarback.calendar.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleCustomRepository {
    List<Schedule> findSchedulesByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Long memberId, Long categoryId);

    List<Schedule> findSchedules(Long memberId, Long categoryId);
}
