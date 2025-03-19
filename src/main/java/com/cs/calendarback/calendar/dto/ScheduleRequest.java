package com.cs.calendarback.calendar.dto;

import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record ScheduleRequest(
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        String title,

        String note,

        @NotBlank(message = "시작 일정은 필수 입력 값입니다.")
        String startDateTime, // String으로 받기

        @NotBlank(message = "종료 일정은 필수 입력 값입니다.")
        String endDateTime, // String으로 받기

        @NotNull(message = "사용자 ID 필수 입력 값입니다.")
        Long memberId
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public LocalDateTime toStartDateTime() {
        return parseDateTime(startDateTime, "시작 일정");
    }

    public LocalDateTime toEndDateTime() {
        return parseDateTime(endDateTime, "종료 일정");
    }

    private LocalDateTime parseDateTime(String dateTime, String fieldName) {
        try {
            return LocalDateTime.parse(dateTime, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new CoreException(ErrorType.INVALID_DATE_FORMAT, fieldName);
        }
    }
}