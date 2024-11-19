package com.homeseek.map.controller;

import com.homeseek.map.dto.*;
import com.homeseek.map.service.MapServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
    private final MapServiceImpl ms;

    @GetMapping("/getEstateByName")
    public ResponseEntity<SearchByNameEstateResp> getEstatesByName(@RequestParam("keyword") String keyword) {
        SearchByNameEstateResp resp = ms.getEstatesByName(keyword);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getDongNames")
    public ResponseEntity<DongOfGuResp> getDongNames(@RequestBody DongOfGuReq req){
        DongOfGuResp resp = ms.getDongNames(req);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getGuNames")
    public ResponseEntity<GuOfSiResp> getGuNames(@RequestBody GuOfSiReq req){
        GuOfSiResp resp = ms.getGuNames(req);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getEstateByToggle")
    public ResponseEntity<SearchByToggleEstateWithResp> getEstatesByToggle(@RequestBody SearchByToggleEstateWithReq req){
        SearchByToggleEstateWithResp resp = ms.getEstatesByToggle(req);
        return ResponseEntity.ok(resp);
    }
}
