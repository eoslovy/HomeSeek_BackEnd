package com.homeseek.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchByNameEstateResp {
    private List<AptDto> aptDtos;
}
