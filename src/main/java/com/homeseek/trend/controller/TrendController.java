package com.homeseek.trend.controller;

import com.homeseek.trend.dto.TrendReq;
import com.homeseek.trend.dto.TrendResp;
import com.homeseek.trend.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trend")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TrendController {

    private final TrendService trendService;

    @PostMapping("/search")
    public ResponseEntity<TrendResp> getTrendData(@RequestBody TrendReq request) {
        try {
            TrendResp result = trendService.getTrendData(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new RuntimeException("데이터 조회 중 오류가 발생했습니다", e);
        }
    }
}