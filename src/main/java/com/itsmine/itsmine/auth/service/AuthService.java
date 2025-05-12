package com.itsmine.itsmine.auth.service;

import com.itsmine.itsmine.auth.domain.User;
import com.itsmine.itsmine.auth.dto.CompleteSignupRequest;
import com.itsmine.itsmine.auth.dto.KakaoUserResponse;
import com.itsmine.itsmine.auth.dto.UserSessionDto;
import com.itsmine.itsmine.auth.exception.AuthErrorCode;
import com.itsmine.itsmine.auth.exception.AuthException;
import com.itsmine.itsmine.auth.repository.UserRepository;
import com.itsmine.itsmine.auth.util.KakaoUtil;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);
    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;

    public UserSessionDto kakaoLogin(String authorizationCode){
        String provider = "kakao";
        String accessToken = kakaoUtil.getAccessToken(authorizationCode);
        KakaoUserResponse kakaoUserResponse = kakaoUtil.getKakaoUserInfo(accessToken);
        String email = kakaoUserResponse.getKakaoAccount().getEmail();
        String nickname = kakaoUserResponse.getKakaoAccount().getProfile().getNickname();
        return userRepository.findByEmail(email)
                .map(user -> {
                    logger.info("기존 회원 로그인: {}", email);
                    return UserSessionDto.builder()
                            .nickname(user.getNickname())
                            .email(user.getEmail())
                            .provider(user.getProvider())
                            .isNewUser(false)
                            .build();
                })
                .orElseGet(()-> {
                    logger.info("신규 회원 로그인 요청: {}", email);
                    return UserSessionDto.builder()
                            .nickname(nickname)
                            .email(email)
                            .provider(provider)
                            .isNewUser(true)
                            .build();

                });
    }

    @Transactional
    public UserSessionDto registerNewSocialSignup(CompleteSignupRequest request) {
        try {
            User user = User.builder()
                    .nickname(request.getNickname())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .provider(request.getProvider())
                    .build();
            return new UserSessionDto(userRepository.save(user), false);

        } catch (Exception e) {
            logger.error("회원가입 중 예외 발생 {}", e);
            throw new AuthException(AuthErrorCode.SIGNUP_FAILED);
        }

    }



}
