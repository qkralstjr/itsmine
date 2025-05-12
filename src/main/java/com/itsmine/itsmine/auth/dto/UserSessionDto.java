package com.itsmine.itsmine.auth.dto;

import com.itsmine.itsmine.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Builder
public class UserSessionDto {
    private String nickname;
    private String email;
    private String provider;
    private boolean isNewUser;

    public UserSessionDto(User user, boolean isNewUser) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.provider = user.getProvider();
        this.isNewUser = isNewUser;
    }

}
