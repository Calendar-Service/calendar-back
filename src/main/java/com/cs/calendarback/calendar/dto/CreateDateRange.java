package com.cs.calendarback.calendar.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

public record CreateDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime){

    public static CreateDateRange monthOf(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return new CreateDateRange(startDateTime, endDateTime);
    }

    public static CreateDateRange dateOf(LocalDate searchDate) {
        LocalDateTime startDateTime = searchDate.atStartOfDay();
        LocalDateTime endDateTime = searchDate.atTime(23, 59, 59);
        return new CreateDateRange(startDateTime, endDateTime);
    }

}
