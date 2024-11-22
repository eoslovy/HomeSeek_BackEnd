package com.homeseek.map.mapper;

import com.homeseek.map.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapMapper {
    List<AptDto> findEstateByName(String keyword);
    List<DongDto> findDongNames(@Param("si") String si, @Param("gu") String gu);
    List<DongDto> findDongNamesSe(@Param("si") String si, @Param("gu") String gu);
    List<GuDto> findGuNames(@Param("si") String si);
    List<GuDto> findGuNamesSe(@Param("si") String si);
    List<SiDto> findSiNames();
    List<SiDto> findSiNamesSe();
    List<ToggleEstateDto> findToggleEstateBySi(@Param("code") String code);
    List<ToggleEstateDto> findToggleEstateByGu(@Param("code") String code);
    List<ToggleEstateDto> findToggleEstateByDong(@Param("code") String code);
    List<HospitalDto> getHospitals();
    List<MarketDto> getMarkets();
    List<SubwayDto> getSubways();
}
