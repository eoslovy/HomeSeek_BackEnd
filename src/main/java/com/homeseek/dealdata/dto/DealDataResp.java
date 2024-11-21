package com.homeseek.dealdata.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealDataResp {
    private Long id;
    private String aptNm;
    private Double excluUseAr;
    private Integer buildYear;
    private Integer dealYear;
    private Integer dealMonth;
    private Integer dealDay;
    private Long dealAmount;
    private String si;
    private String gu;
    private String dong;
}