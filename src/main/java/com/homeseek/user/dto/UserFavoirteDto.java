package com.homeseek.user.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavoirteDto {
    private int id;
    private String userId;
    private String aptSeq;
}
