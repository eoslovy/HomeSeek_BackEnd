package com.homeseek.dealdata.controller;

import com.homeseek.dealdata.dto.DealDataReq;
import com.homeseek.dealdata.dto.DealDataResp;
import com.homeseek.dealdata.service.DealDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="실거래")
@RestController
@RequestMapping("/deals")
@RequiredArgsConstructor
public class DealDataController {

    private final DealDataService dealDataService;

    @Operation(summary = "실거래가 조회")
    @PostMapping("/search")
    public ResponseEntity<List<DealDataResp>> getDealsByAptName(@RequestBody DealDataReq request) {
        List<DealDataResp> deals = dealDataService.findDealsByAptName(
                request.getAptName(),
                request.getSi(),
                request.getGu()
        );
        System.out.println(request.getAptName() + " " + request.getSi() + " " + request.getGu());
        return ResponseEntity.ok(deals);
    }
}
