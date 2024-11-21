package com.homeseek.sale.service;

import com.homeseek.sale.dto.SaleResp;

import java.util.List;

public interface SaleService {
    List<SaleResp> findSalesByAptName(String aptName, String si, String gu);
}
