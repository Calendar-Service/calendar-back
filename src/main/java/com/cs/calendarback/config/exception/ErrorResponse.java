package com.cs.calendarback.config.exception;

public record ErrorResponse(
        ErrorCode errorCode,
        String message,
        Object payload
) {
}