package com.cs.calendarback.login.service;

import com.cs.calendarback.config.exception.CoreException;
import com.cs.calendarback.config.exception.ErrorType;
import com.cs.calendarback.login.dto.KakaoTokenResponse;
import com.cs.calendarback.login.dto.KakaoMemberResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    @Value("${kakao.client_id}")
    private String clientId;

    private static final String KAKAO_AUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private static final String KAKAO_AUTH_USER_URL_HOST = "https://kapi.kakao.com";


    public KakaoTokenResponse getAccessTokenFromKakao(String code) {

        KakaoTokenResponse kakaoTokenResponseDto = WebClient.create(KAKAO_AUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder.scheme("https").path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new CoreException(ErrorType.KAKAO_INVALID_PARAMETER, clientId)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new CoreException(ErrorType.KAKAO_INTERNAL_SERVER_ERROR, clientId)))
                .bodyToMono(KakaoTokenResponse.class)
                .block();

        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());

        return kakaoTokenResponseDto;
    }

    public KakaoMemberResponse getUserInfo(String accessToken) {

        KakaoMemberResponse userInfo = WebClient.create(KAKAO_AUTH_USER_URL_HOST).get()
                .uri(uriBuilder -> uriBuilder.scheme("https").path("/v2/user/me").build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INVALID_PARAMETER, clientId)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new CoreException(ErrorType.KAKAO_INTERNAL_SERVER_ERROR, clientId)))
                .bodyToMono(KakaoMemberResponse.class)
                .block();

        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());

        return userInfo;
    }

    public void kakaoUnlink(String accessToken) {
        String url = "https://kapi.kakao.com/v1/user/logout";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }
}
