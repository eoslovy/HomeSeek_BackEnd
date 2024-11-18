package com.homeseek.map.service;

import com.homeseek.map.dto.*;
import com.homeseek.map.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
        // 정렬 필드 및 방향 설정
        Sort sort = Sort.by(Sort.Direction.ASC, "aptNm"); // 기본값
        if (req.getSortBy() != null && !req.getSortBy().isEmpty()) {
            sort = Sort.by(Sort.Direction.ASC, req.getSortBy());
        }

        List<AptDto> list = mr.findAptByName(req.getKeyword(), sort);
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
