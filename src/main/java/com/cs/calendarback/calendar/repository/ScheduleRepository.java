package com.cs.calendarback.calendar.repository;


import com.cs.calendarback.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleCustomRepository {
}
