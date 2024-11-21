package com.homeseek.sale.controller;

import com.homeseek.sale.dto.SaleReq;
import com.homeseek.sale.dto.SaleResp;
import com.homeseek.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping("/search")
    public ResponseEntity<List<SaleResp>> getSalesByAptName(@RequestBody SaleReq request) {
        List<SaleResp> deals = saleService.findSalesByAptName(
                request.getAptName(),
                request.getSi(),
                request.getGu()
        );
        System.out.println(request.getAptName() + " " + request.getSi() + " " + request.getGu());
        return ResponseEntity.ok(deals);
    }
}
