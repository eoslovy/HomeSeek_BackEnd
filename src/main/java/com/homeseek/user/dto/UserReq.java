package com.homeseek.user.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReq {
    private Integer id;
    private String userId;
    private String pw;
    private String nickname;
}
