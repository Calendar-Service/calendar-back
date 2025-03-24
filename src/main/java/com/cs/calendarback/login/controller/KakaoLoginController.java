package com.cs.calendarback.login.controller;

import com.cs.calendarback.login.dto.KakaoLoginResponse;
import com.cs.calendarback.login.dto.KakaoTokenResponse;
import com.cs.calendarback.member.service.MemberService;
import com.cs.calendarback.login.dto.KakaoMemberResponse;
import com.cs.calendarback.login.service.KakaoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/logins")
public class KakaoLoginController {
    private final KakaoService kakaoService;
    private final MemberService memberService;

    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code, HttpServletResponse response) {

        KakaoTokenResponse kakaoToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoMemberResponse kakaoMember = kakaoService.getMemberInfo(kakaoToken.getAccessToken());
        Long memberId = memberService.createKakaoMember(kakaoMember);
        KakaoLoginResponse kakaoLogin = KakaoLoginResponse.of(memberId);

        response.setHeader("Authorization", "Bearer " + kakaoToken.getAccessToken());
        response.addCookie(createCookie("refresh", kakaoToken.getRefreshToken()));

        return ResponseEntity.ok(kakaoLogin);
    }


    @GetMapping("/oauth/kakao/logout")
    public ResponseEntity<?> kakaoLogout(@RequestHeader("Authorization") String accessToken, HttpServletResponse response) {
        kakaoService.kakaoLogout(accessToken);
        response.addCookie(deleteCookie("refresh"));
        return ResponseEntity.ok("카카오 로그아웃 완료");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        // cookie.setSecure(true);
        // cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    private Cookie deleteCookie(String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);  // 쿠키 즉시 삭제
        cookie.setPath("/");   // 모든 경로에서 삭제 적용
        return cookie;
    }
}
