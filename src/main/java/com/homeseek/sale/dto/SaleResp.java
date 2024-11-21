package com.homeseek.sale.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleResp {
    private Long id;
    private String aptNm;
    private Double excluUseAr;
    private Long price;
    private Double latitude;
    private Double longitude;
    private String description;
    private String uploadDate;
    private String agentNm;
    private String si;
    private String gu;
}
