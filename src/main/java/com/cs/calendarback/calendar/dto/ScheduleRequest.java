package com.cs.calendarback.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ScheduleRequest(
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        String title,

        String note,

        @NotBlank(message = "시작 일정은 필수 입력 값입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startDateTime,

        @NotBlank(message = "종료 일정은 필수 입력 값입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime endDateTime,

        @NotBlank(message = "사용자 ID 필수 입력 값입니다.")
        Long userId
) {
}
