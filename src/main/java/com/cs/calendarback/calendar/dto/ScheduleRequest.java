package com.cs.calendarback.calendar.dto;

import jakarta.validation.constraints.NotNull;

public record ScheduleRequest(
        @NotNull(message = "사용자 ID 필수 입력 값입니다.")
        Long memberId,

        Long categoryId
) {
}
