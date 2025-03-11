package com.cs.calendarback.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    SCHEDULE_NOT_FOUND(ErrorCode.NOT_FOUND, "일정을 찾을 수 없습니다.", LogLevel.WARN)
    , MEMBER_NOT_FOUND(ErrorCode.NOT_FOUND, "회원을 찾을 수 없습니다.", LogLevel.WARN)
    , EMAIL_ALREADY_EXISTS(ErrorCode.CONFLICT, "존재하는 이메일 아이디 입니다.", LogLevel.WARN)
    ;
    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;
}