package com.cs.calendarback.calendar.dto;

import com.cs.calendarback.calendar.entity.Calendar;

import java.time.LocalDateTime;
import java.util.List;

public record CalendarResponse(
        Long id,
        String title,
        String note,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Long userId
) {
    public static CalendarResponse from(Calendar calendar) {
        return new CalendarResponse(
                calendar.getId(),
                calendar.getTitle(),
                calendar.getNote(),
                calendar.getStartDateTime(),
                calendar.getEndDateTime(),
                calendar.getUserId()
        );
    }

    public static List<CalendarResponse> from(List<Calendar> calendars) {
        return calendars.stream()
                .map(CalendarResponse::from)
                .toList();
    }
}
