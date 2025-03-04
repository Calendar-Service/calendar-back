package com.cs.calendarback.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    ;

    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;
}