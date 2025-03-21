package com.cs.calendarback.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    SCHEDULE_NOT_FOUND(ErrorCode.NOT_FOUND, "일정을 찾을 수 없습니다.", LogLevel.WARN),
    INVALID_DATE_FORMAT(ErrorCode.BAD_REQUEST, "날짜의 형식이 올바르지 않습니다.", LogLevel.WARN)
    , MEMBER_NOT_FOUND(ErrorCode.NOT_FOUND, "회원을 찾을 수 없습니다.", LogLevel.WARN)
    , EMAIL_ALREADY_EXISTS(ErrorCode.CONFLICT, "존재하는 이메일 아이디 입니다.", LogLevel.WARN)
    , INVALID_DATE_RANGE(ErrorCode.BAD_REQUEST, "시작 날짜가 종료 날짜보다 클 수 없습니다.", LogLevel.WARN)
    , KAKAO_INVALID_PARAMETER(ErrorCode.BAD_REQUEST, "카카오 API 요청이 잘못되었습니다.", LogLevel.WARN)
    , KAKAO_INTERNAL_SERVER_ERROR(ErrorCode.KAKAO, "카카오 API 서버 오류", LogLevel.ERROR);

    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;
}