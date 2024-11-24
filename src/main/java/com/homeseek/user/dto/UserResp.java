package com.homeseek.user.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResp {
    private int id;
    private String userId;
    private String pw;
    private String nickname;
    private boolean autoLogin;
    private String accessToken;
    private String refreshToken;
}
