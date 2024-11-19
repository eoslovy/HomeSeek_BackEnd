package com.homeseek.map.mapper;

import com.homeseek.map.dto.AptDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapMapper {
    List<AptDto> findEstateByName(String keyword);
}
