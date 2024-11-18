package com.homeseek.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AptDto {
    private String aptName;
    private String sidoName;
    private String gugunName;
    private String umd;
    private String roadNm;
    private String roadNmBonbun;
    private String roadNmBubun;
    private String floor;
    private Integer buildYear;
    private String latitude;
    private String longitude;
}
