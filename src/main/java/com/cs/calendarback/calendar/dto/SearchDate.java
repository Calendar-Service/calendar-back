package com.cs.calendarback.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SearchDate(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate searchDate
) {
    public LocalDateTime getStartOfDay() {
        return searchDate.atStartOfDay();
    }

    public LocalDateTime getEndOfDay() {
        return searchDate.atTime(23, 59, 59);
    }

}
