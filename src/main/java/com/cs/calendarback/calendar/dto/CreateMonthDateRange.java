package com.cs.calendarback.calendar.dto;

import java.time.LocalDateTime;
import java.time.YearMonth;

public record CreateMonthDateRange (LocalDateTime startDateTime, LocalDateTime endDateTime){

    public static CreateMonthDateRange of(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return new CreateMonthDateRange(startDateTime, endDateTime);
    }
}
