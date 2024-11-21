package com.homeseek.dealdata.service;

import com.homeseek.dealdata.dto.DealDataResp;
import com.homeseek.dealdata.mapper.DealDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealDataServiceImpl implements DealDataService {

    private final DealDataMapper dealDataMapper;

    @Override
    public List<DealDataResp> findDealsByAptName(String aptName, String si, String gu) {
        return dealDataMapper.findDealsByAptName(aptName, si, gu);
    }
}
