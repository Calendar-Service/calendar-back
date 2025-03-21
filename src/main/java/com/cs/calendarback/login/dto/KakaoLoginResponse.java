package com.cs.calendarback.login.dto;

public record KakaoLoginResponse(Long memberId) {
    public static KakaoLoginResponse of(Long memberId) {
        return new KakaoLoginResponse(memberId);
    }
}