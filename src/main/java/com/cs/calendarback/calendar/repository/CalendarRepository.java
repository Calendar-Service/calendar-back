package com.cs.calendarback.calendar.repository;


import com.cs.calendarback.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query("SELECT c FROM Calendar c WHERE c.startDateTime >= :startDateTime AND c.endDateTime <= :endDateTime")
    List<Calendar> findCalendarsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
