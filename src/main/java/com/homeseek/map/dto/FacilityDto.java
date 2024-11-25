package com.homeseek.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDto<T> {
    private T facility;
    private double distance;  // 킬로미터 단위
}
