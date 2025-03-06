package com.cs.calendarback.calendar.repository;


import com.cs.calendarback.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT c FROM Schedule c WHERE c.startDateTime >= :startDateTime AND c.endDateTime <= :endDateTime")
    List<Schedule> findSchedulesByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
