package com.homeseek.user.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavoirteReq {
    private String userId;
    private String aptSeq;
}
