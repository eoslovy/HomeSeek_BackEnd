package com.homeseek.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToggleEstateDto {
    private String aptSeq;
    private String aptName;
    private String si;
    private String gu;
    private String dong;
    private String latitude;
    private String longitude;
}
