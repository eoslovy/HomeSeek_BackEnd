package com.homeseek.trend.service;

import com.homeseek.trend.dto.DistrictReq;
import com.homeseek.trend.dto.TrendReq;
import com.homeseek.trend.dto.TrendResp;

public interface TrendService {
    TrendResp getTrendData(TrendReq trendReq);
    TrendResp getDistrictTrendData(DistrictReq request);
}