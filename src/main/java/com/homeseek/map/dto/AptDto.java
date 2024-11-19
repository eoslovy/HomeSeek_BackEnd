package com.homeseek.map.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptDto {
    private String aptName;
    private String sidoName;
    private String gugunName;
    private String umdNm;
    private String roadNm;
    private String roadNmBonbun;
    private String roadNmBubun;
    private String dealAmount;
    private String floor;
    private Integer buildYear;
    private String latitude;
    private String longitude;
}
