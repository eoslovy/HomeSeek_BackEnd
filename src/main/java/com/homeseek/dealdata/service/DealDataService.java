package com.homeseek.dealdata.service;

import com.homeseek.dealdata.dto.DealDataResp;

import java.util.List;

public interface DealDataService {
    List<DealDataResp> findDealsByAptName(String aptName, String si, String gu);
}