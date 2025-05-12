package com.itsmine.itsmine.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsmine.itsmine.auth.dto.KakaoTokenResponse;
import com.itsmine.itsmine.auth.dto.KakaoUserResponse;
import com.itsmine.itsmine.auth.exception.AuthErrorCode;
import com.itsmine.itsmine.auth.exception.AuthException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoUtil {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(KakaoUtil.class);


    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;


    // 엑세스 토큰 요청
    public String getAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try{
            ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                    "https://kauth.kakao.com/oauth/token",
                    request,
                    KakaoTokenResponse.class);

            if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getAccessToken();
            } else {
                throw new AuthException(AuthErrorCode.OAUTH_TOKEN_REQUEST_FAILED);
            }
        }catch (Exception e){
            throw new AuthException(AuthErrorCode.OAUTH_TOKEN_REQUEST_FAILED);
        }

    }

    // 카카오 유저 정보
    public KakaoUserResponse getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserResponse> response = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                KakaoUserResponse.class
        );

        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            logger.info("카카오 사용자 정보 조회 성공 {}", response.getBody() );
            return response.getBody();
        } else {
            throw new RuntimeException("카카오 사용자 정보 조회 실패");
        }

    }



}
