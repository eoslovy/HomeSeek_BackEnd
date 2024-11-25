package com.homeseek.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoLoginResp {
    private int id;
    private String userId;
    private String pw;
    private String nickname;
    private boolean autoLogin;
    private String accessToken;
    private String refreshToken;
}
