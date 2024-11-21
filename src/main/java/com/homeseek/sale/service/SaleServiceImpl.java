package com.homeseek.sale.service;

import com.homeseek.sale.dto.SaleResp;
import com.homeseek.sale.mapper.SaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService{

    private final SaleMapper saleMapper;

    @Override
    public List<SaleResp> findSalesByAptName(String aptName, String si, String gu) {
        return saleMapper.findSalesByAptName(aptName, si, gu);
    }
}
