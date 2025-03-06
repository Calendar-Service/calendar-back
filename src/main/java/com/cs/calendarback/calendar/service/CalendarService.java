package com.cs.calendarback.calendar.service;

import com.cs.calendarback.calendar.dto.CalendarRequest;
import com.cs.calendarback.calendar.entity.Calendar;
import com.cs.calendarback.calendar.repository.CalendarRepository;
import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public List<Calendar> getCalendars() {
        return calendarRepository.findAll();
    }

    public Calendar create(CalendarRequest request) {
        Calendar calendar =  Calendar.create(request.title(), request.note(), request.startDateTime(), request.endDateTime(), request.userId());
         return calendarRepository.save(calendar);
    }

    public Calendar getCalendar(Long id) {
        return calendarRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.CALENDAR_NOT_FOUND, id));
    }

    public List<Calendar> getSearchDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return calendarRepository.findCalendarsByDateRange(startDateTime, endDateTime);
    }

    public Calendar update(Long id, CalendarRequest request) {
        calendarRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.CALENDAR_NOT_FOUND, id));
        Calendar calendar = Calendar.update(id, request.title(), request.note(), request.startDateTime(), request.endDateTime(), request.userId());
        return calendarRepository.save(calendar);
    }

    public void delete(Long id) {
        calendarRepository.delete(calendarRepository.findById(id).orElseThrow(() -> new CoreException(ErrorType.CALENDAR_NOT_FOUND, id)));
    }

}
