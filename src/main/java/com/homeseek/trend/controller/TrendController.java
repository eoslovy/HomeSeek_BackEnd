package com.homeseek.trend.controller;

import com.homeseek.trend.dto.DistrictReq;
import com.homeseek.trend.dto.TrendReq;
import com.homeseek.trend.dto.TrendResp;
import com.homeseek.trend.service.TrendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="트렌드")
@RestController
@RequestMapping("/trend")
@RequiredArgsConstructor
public class TrendController {

    private final TrendService trendService;

    @Operation(summary = "트렌드 분석")
    @PostMapping("/search")
    public ResponseEntity<TrendResp> getDistrictTrendData(@RequestBody DistrictReq request) {
        try {
            TrendResp result = trendService.getDistrictTrendData(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new RuntimeException("데이터 조회 중 오류가 발생했습니다", e);
        }
    }

//    @PostMapping("/search")
//    public ResponseEntity<TrendResp> getTrendData(@RequestBody TrendReq request) {
//        try {
//            TrendResp result = trendService.getTrendData(request);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            throw new RuntimeException("데이터 조회 중 오류가 발생했습니다", e);
//        }
//    }
}