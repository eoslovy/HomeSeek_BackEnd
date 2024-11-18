package com.homeseek.map.service;

import com.homeseek.map.dto.*;

public interface MapService {
    SearchByNameEstateResp getEstatesByName(SearchByNameEstateReq req);
    DongOfGuResp getDongNames(DongOfGuReq req);
    GuOfSiResp getGuNames(GuOfSiReq req);
    SearchByToggleEstateWithResp getEstatesByToggle(SearchByToggleEstateWithReq req);
}
