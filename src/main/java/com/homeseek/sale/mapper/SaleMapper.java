package com.homeseek.sale.mapper;

import com.homeseek.sale.dto.SaleResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SaleMapper {
    List<SaleResp> findSalesByAptName(
            @Param("aptName") String aptName,
            @Param("si") String si,
            @Param("gu") String gu
    );
}
