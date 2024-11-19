package com.homeseek.map.service;

import com.homeseek.map.dto.*;

public interface MapService {
    SearchByNameEstateResp getEstatesByName(String keyword);
    DongOfGuResp getDongNames(DongOfGuReq req);
    GuOfSiResp getGuNames(GuOfSiReq req);
    SearchByToggleEstateWithResp getEstatesByToggle(SearchByToggleEstateWithReq req);
}
