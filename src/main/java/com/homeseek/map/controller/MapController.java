package com.homeseek.map.controller;

import com.homeseek.map.dto.*;
import com.homeseek.map.service.MapServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name="지도")
@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
    private final MapServiceImpl ms;

    @Operation(summary = "이름으로 검색")
    @GetMapping("/getEstateByName")
    public ResponseEntity<List<AptDto>> getEstatesByName(@RequestParam("keyword") String keyword) {
         return ResponseEntity.ok(ms.getEstatesByName(keyword));
    }

    @Operation(summary = "동 이름 가져오기")
    @GetMapping("/getDongNames")
    public ResponseEntity<List<DongDto>> getDongNames(@RequestParam("si") String si, @RequestParam("gu") String gu){
        return ResponseEntity.ok(ms.getDongNames(si, gu));
    }

    @Operation(summary = "구 이름 가져오기")
    @GetMapping("/getGuNames")
    public ResponseEntity<List<GuDto>> getGuNames(@RequestParam("si") String si){
        return ResponseEntity.ok(ms.getGuNames(si));
    }

    @Operation(summary = "시 이름 가져오기")
    @GetMapping("/getSiNames")
    public ResponseEntity<List<SiDto>> getSiNames(){
        return ResponseEntity.ok(ms.getSiNames());
    }

    @Operation(summary = "시 별로 조회")
    @GetMapping("/getEstatesByToggleWithSi")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithSi(@RequestParam("code") String code){
        return ResponseEntity.ok(ms.getEstatesByToggleWithSi(code));
    }

    @Operation(summary = "구 별로 조회")
    @GetMapping("/getEstatesByToggleWithGu")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithGu(@RequestParam("code") String code){
        return ResponseEntity.ok(ms.getEstatesByToggleWithGu(code));
    }

    @Operation(summary = "동 별로 조회")
    @GetMapping("/getEstatesByToggleWithDong")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithDong(@RequestParam("code") String code){
        return ResponseEntity.ok(ms.getEstatesByToggleWithDong(code));
    }

    @Operation(summary = "시설 조회")
    @GetMapping("/getFacilities/{type}")
    public ResponseEntity<List<?>> getHospitals(@PathVariable String type){
        if(type.equals("hospitals")){
            return ResponseEntity.ok(ms.getHospitals());
        }else if(type.equals("markets")){
            return ResponseEntity.ok(ms.getMarkets());
        } else{
            return ResponseEntity.ok(ms.getSubways());
        }
    }

    @Operation(summary = "아파트 주변 1km 시설 검색")
    @GetMapping("/getNearbyFacilities")
    public ResponseEntity<Map<String, List<?>>> getNearbyFacilities(@RequestParam("aptName") String aptName) {
        return ResponseEntity.ok(ms.getNearbyFacilities(aptName));
    }
}
