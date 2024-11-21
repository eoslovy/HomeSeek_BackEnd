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
        List<DongDto> list = mm.findDongNames(si, gu);
        if(si.equals("세종특별자치시")){
            list.addAll(mm.findDongNamesSe(si,gu));
        }
        return list;
    }

    @Override
    public List<GuDto> getGuNames(String si) {
        List<GuDto> list = mm.findGuNames(si);
        if(si.equals("세종특별자치시")){
            list.addAll(mm.findGuNamesSe(si));
        }
        return list;
    }

    @Override
    public List<SiDto> getSiNames() {
        List<SiDto> list = mm.findSiNames();
        list.addAll(mm.findSiNamesSe());
        return list;
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithSi(String code) {
        return mm.findToggleEstateBySi(code);
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithGu(String code) {
        return mm.findToggleEstateByGu(code);
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithDong(String code) {
        return mm.findToggleEstateByDong(code);
    }
}
