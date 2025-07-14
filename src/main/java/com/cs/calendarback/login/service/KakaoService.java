//package com.cs.calendarback.login.service;
//
//import com.cs.calendarback.config.exception.CoreException;
//import com.cs.calendarback.config.exception.ErrorType;
//import com.cs.calendarback.login.dto.KakaoTokenResponse;
//import com.cs.calendarback.login.dto.KakaoMemberResponse;
//import io.netty.handler.codec.http.HttpHeaderValues;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class KakaoService {
//
//    @Value("${kakao.client_id}")
//    private String clientId;
//
//    private static final String KAKAO_AUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
//    private static final String KAKAO_AUTH_USER_URL_HOST = "https://kapi.kakao.com";
//    private static final String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";
//
//
//    public KakaoTokenResponse getAccessTokenFromKakao(String code) {
//
//        KakaoTokenResponse kakaoTokenResponseDto = WebClient.create(KAKAO_AUTH_TOKEN_URL_HOST).post()
//                .uri(uriBuilder -> uriBuilder.scheme("https").path("/oauth/token")
//                        .queryParam("grant_type", "authorization_code")
//                        .queryParam("client_id", clientId)
//                        .queryParam("code", code)
//                        .build(true))
//                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INVALID_PARAMETER, "getAccessTokenForKakao")))
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INTERNAL_SERVER_ERROR, "getAccessTokenForKakao")))
//                .bodyToMono(KakaoTokenResponse.class)
//                .block();
//
//        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
//        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
//
//        return kakaoTokenResponseDto;
//    }
//
//    public KakaoMemberResponse getMemberInfo(String accessToken) {
//
//        KakaoMemberResponse userInfo = WebClient.create(KAKAO_AUTH_USER_URL_HOST).get()
//                .uri(uriBuilder -> uriBuilder.scheme("https").path("/v2/user/me").build(true))
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INVALID_PARAMETER,  "memberInfo")))
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INTERNAL_SERVER_ERROR, "memberInfo")))
//                .bodyToMono(KakaoMemberResponse.class)
//                .block();
//
//        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
//        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
//
//        return userInfo;
//    }
//
//
//    public void kakaoLogout(String accessToken) {
//        WebClient.create()
//                .post()
//                .uri(KAKAO_LOGOUT_URL)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INVALID_PARAMETER, "logout")))
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INTERNAL_SERVER_ERROR, "logout")))
//                .bodyToMono(String.class)
//                .block();
//    }
//}
