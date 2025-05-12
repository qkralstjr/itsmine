package com.itsmine.itsmine.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoUserResponse {
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;



    @Getter
    @Setter
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Getter
        @Setter
        public static class Profile {
            private String nickname;
        }
    }
}
