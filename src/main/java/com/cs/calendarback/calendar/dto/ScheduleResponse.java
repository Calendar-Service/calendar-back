package com.cs.calendarback.calendar.dto;

import com.cs.calendarback.calendar.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleResponse(
        Long id,
        String title,
        String note,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Long memberId
) {
    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getNote(),
                schedule.getStartDateTime(),
                schedule.getEndDateTime(),
                schedule.getMemberId()
        );
    }

    public static List<ScheduleResponse> from(List<Schedule> Schedules) {
        return Schedules.stream()
                .map(ScheduleResponse::from)
                .toList();
    }
}
