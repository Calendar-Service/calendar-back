package com.cs.calendarback.calendar.dto;

import com.cs.calendarback.calendar.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record ScheduleResponse(
        Long id,
        String title,
        String note,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        String startDateTime,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        String endDateTime,
        Long userId
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getNote(),
                schedule.getStartDateTime().format(FORMATTER),
                schedule.getEndDateTime().format(FORMATTER),
                schedule.getUserId()
        );
    }

    public static List<ScheduleResponse> from(List<Schedule> schedules) {
        return schedules.stream()
                .map(ScheduleResponse::from)
                .toList();
    }
}
