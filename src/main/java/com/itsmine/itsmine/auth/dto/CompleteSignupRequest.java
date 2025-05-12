package com.itsmine.itsmine.auth.dto;

import lombok.Data;

@Data
public class CompleteSignupRequest {
    private String email;
    private String nickname;
    private String phone;
    private String provider;

}
