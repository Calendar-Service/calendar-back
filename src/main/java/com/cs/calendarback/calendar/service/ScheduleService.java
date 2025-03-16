package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.ScheduleRequest;
import com.cs.calendarback.calendar.entity.Schedule;
import com.cs.calendarback.calendar.repository.ScheduleRepository;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByYearAndMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return scheduleRepository.findSchedulesByDateRange(startDateTime, endDateTime);
    }

    public List<Schedule> getSearchDates(LocalDate searchDate) {
        LocalDateTime startDateTime = searchDate.atStartOfDay();
        LocalDateTime endDateTime = searchDate.atTime(23, 59, 59);
        return scheduleRepository.findSchedulesByDateRange(startDateTime, endDateTime);
    }

    public Schedule create(ScheduleRequest request) {
        Schedule schedule = Schedule.create(request.title(), request.note(), request.toStartDateTime(), request.toEndDateTime(), request.userId());
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
    }

    public Schedule update(Long id, ScheduleRequest request) {
        scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id));
        Schedule schedule = Schedule.update(id, request.title(), request.note(), request.toStartDateTime(), request.toEndDateTime(), request.userId());
        return scheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        scheduleRepository.delete(scheduleRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.SCHEDULE_NOT_FOUND, id)));
    }

}
