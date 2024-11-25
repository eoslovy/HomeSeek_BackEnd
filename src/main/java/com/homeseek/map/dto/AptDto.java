package com.homeseek.map.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptDto {
    private String aptSeq;
    private String aptName;
    private String sidoName;
    private String gugunName;
    private String dongName;
    private Integer buildYear;
    private String latitude;
    private String longitude;
}
