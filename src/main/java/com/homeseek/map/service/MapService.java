package com.homeseek.map.service;

import com.homeseek.map.dto.*;

import java.util.List;
import java.util.Map;

public interface MapService {
    List<AptDto> getEstatesByName(String keyword);
    List<DongDto> getDongNames(String si, String gu);
    List<GuDto> getGuNames(String si);
    List<SiDto> getSiNames();
    List<ToggleEstateDto> getEstatesByToggleWithSi(String code);
    List<ToggleEstateDto> getEstatesByToggleWithGu(String code);
    List<ToggleEstateDto> getEstatesByToggleWithDong(String code);
    List<HospitalDto> getHospitals();
    List<MarketDto> getMarkets();
    List<SubwayDto> getSubways();
    List<SchoolDto> getSchools();
    Map<String, List<?>> getNearbyFacilities(String aptName);  // 새로 추가할 메서드
}
