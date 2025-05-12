package com.itsmine.itsmine.auth.service;

import com.itsmine.itsmine.auth.dto.KakaoUserResponse;
import com.itsmine.itsmine.auth.repository.UserRepository;
import com.itsmine.itsmine.auth.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);
    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;

    public KakaoUserResponse getKakaoUserInfo(String authorizationCode) {
        String token = kakaoUtil.getAccessToken(authorizationCode);
        return kakaoUtil.getKakaoUserInfo(token);

    }




}
