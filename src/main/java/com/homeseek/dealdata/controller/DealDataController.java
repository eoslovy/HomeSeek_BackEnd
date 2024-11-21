package com.homeseek.dealdata.controller;

import com.homeseek.dealdata.dto.DealDataReq;
import com.homeseek.dealdata.dto.DealDataResp;
import com.homeseek.dealdata.service.DealDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deals")
@RequiredArgsConstructor
public class DealDataController {

    private final DealDataService dealDataService;

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
