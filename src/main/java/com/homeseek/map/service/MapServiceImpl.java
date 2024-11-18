package com.homeseek.map.service;

import com.homeseek.map.dto.*;
import com.homeseek.map.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final MapRepository mr;

    @Override
    public SearchByNameEstateResp getEstatesByName(SearchByNameEstateReq req) {
        List<AptDto> list = mr.findEstatesByName(req.getKeyword(), req.getSortBy());
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
