package com.homeseek.dealdata.mapper;

import com.homeseek.dealdata.dto.DealDataResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DealDataMapper {
    List<DealDataResp> findDealsByAptName(
            @Param("aptName") String aptName,
            @Param("si") String si,
            @Param("gu") String gu
    );
}