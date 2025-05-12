package com.itsmine.itsmine.auth.exception;

import com.itsmine.itsmine.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpStatusCodeException;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode{

    USER_NOT_FOUND("AUTH_001", "회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NEED_PHONE_NUMBER("AUTH_002", "휴대폰 번호가 필요합니다.", HttpStatus.BAD_REQUEST),
    INVALID_OAUTH_PROVIDER("AUTH_003", "잘못된 소셜 로그인입니다.", HttpStatus.BAD_REQUEST),
    INVALID_JWT_TOKEN("AUTH_004", "JWT 토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    OAUTH_TOKEN_REQUEST_FAILED("AUTH_005", "소셜 로그인 토큰 발급 실패", HttpStatus.BAD_GATEWAY),
    USER_ALREADY_REGISTERE("AUTH_008", "사용자 정보 조회 실패", HttpStatus.BAD_GATEWAY),
    SIGNUP_FAILED("AUTH_009", "회원가입에 실패했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
    private final String code;
    private final String message;
    private final HttpStatus status;

}