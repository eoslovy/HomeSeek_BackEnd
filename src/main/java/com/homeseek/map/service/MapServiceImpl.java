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
    public List<AptDto> getEstatesByName(String keyword) {
        return mm.findEstateByName(keyword);
    }

    @Override
    public List<DongDto> getDongNames(String si, String gu) {
        return mm.findDongNames(si, gu);
    }

    @Override
    public List<GuDto> getGuNames(String si) {
        return mm.findGuNames(si);
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithSi(String si) {
        return null;
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithGu(String gu) {
        return null;
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithDong(String dong) {
        return null;
    }
}
