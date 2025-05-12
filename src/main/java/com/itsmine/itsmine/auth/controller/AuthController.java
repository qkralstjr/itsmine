package com.itsmine.itsmine.auth.controller;


import com.itsmine.itsmine.auth.dto.CompleteSignupRequest;
import com.itsmine.itsmine.auth.dto.UserSessionDto;
import com.itsmine.itsmine.auth.service.AuthService;
import com.itsmine.itsmine.auth.service.OAuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final HttpSession session;

    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code")String authorizationCode) {
        logger.info("Kakao 인가 코드 : " + authorizationCode);
        UserSessionDto sessionUser = authService.kakaoLogin(authorizationCode);

        if(sessionUser.isNewUser()){
            return ResponseEntity.status(HttpStatus.OK).body(sessionUser);
        } else {
            session.setAttribute("user", sessionUser);
            return ResponseEntity.ok(sessionUser);
        }
    }

    @PostMapping("/social/signup")
    public ResponseEntity<?> completeSocialSignup(@RequestBody CompleteSignupRequest request) {

        UserSessionDto sessionUser = authService.registerNewSocialSignup(request);
        session.setAttribute("user", sessionUser);
        return ResponseEntity.ok(sessionUser);
    }


}
