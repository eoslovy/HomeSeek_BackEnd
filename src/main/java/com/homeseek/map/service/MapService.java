package com.homeseek.map.service;

import com.homeseek.map.dto.*;

import java.util.List;

public interface MapService {
    List<AptDto> getEstatesByName(String keyword);
    List<DongDto> getDongNames(String si, String gu);
    List<GuDto> getGuNames(String si);
    List<SiDto> getSiNames();
    List<ToggleEstateDto> getEstatesByToggleWithSi(String si);
    List<ToggleEstateDto> getEstatesByToggleWithGu(String gu);
    List<ToggleEstateDto> getEstatesByToggleWithDong(String dong);
}
