package com.homeseek.map.service;

import com.homeseek.map.dto.*;
import com.homeseek.map.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapServiceImpl implements MapService {

    private final MapMapper mm;

    @Override
    public SearchByNameEstateResp getEstatesByName(String keyword) {
        List<AptDto> list = mm.findEstateByName(keyword);
        return new SearchByNameEstateResp(list);
    }

    @Override
    public DongOfGuResp getDongNames(DongOfGuReq req) {
        return null;
    }

    @Override
    public GuOfSiResp getGuNames(GuOfSiReq req) {
        return null;
    }

    @Override
    public SearchByToggleEstateWithResp getEstatesByToggle(SearchByToggleEstateWithReq req) {
        return null;
    }
}
