package com.cs.calendarback.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    SCHEDULE_NOT_FOUND(ErrorCode.NOT_FOUND, "일정을 찾을 수 없습니다.", LogLevel.WARN),
    INVALID_DATE_FORMAT(ErrorCode.BAD_REQUEST, "날짜의 형식이 올바르지 않습니다.", LogLevel.WARN);

    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;
}