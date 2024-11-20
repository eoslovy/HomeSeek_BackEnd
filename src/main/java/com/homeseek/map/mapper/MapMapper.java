package com.homeseek.map.mapper;

import com.homeseek.map.dto.AptDto;
import com.homeseek.map.dto.DongDto;
import com.homeseek.map.dto.GuDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapMapper {
    List<AptDto> findEstateByName(String keyword);
    List<DongDto> findDongNames(@Param("si") String si, @Param("gu") String gu);
    List<GuDto> findGuNames(@Param("si") String si);
}
