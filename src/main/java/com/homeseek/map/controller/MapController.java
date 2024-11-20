package com.homeseek.map.controller;

import com.homeseek.map.dto.*;
import com.homeseek.map.service.MapServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
    private final MapServiceImpl ms;

    @GetMapping("/getEstateByName")
    public ResponseEntity<List<AptDto>> getEstatesByName(@RequestParam("keyword") String keyword) {
         return ResponseEntity.ok(ms.getEstatesByName(keyword));
    }

    @GetMapping("/getDongNames")
    public ResponseEntity<List<DongDto>> getDongNames(@RequestParam("si") String si, @RequestParam("gu") String gu){
        return ResponseEntity.ok(ms.getDongNames(si, gu));
    }

    @GetMapping("/getGuNames")
    public ResponseEntity<List<GuDto>> getGuNames(@RequestParam("si") String si){
        return ResponseEntity.ok(ms.getGuNames(si));
    }

    @GetMapping("/getSiNames")
    public ResponseEntity<List<SiDto>> getSiNames(){
        return ResponseEntity.ok(ms.getSiNames());
    }

    @GetMapping("/getEstatesByToggleWithSi")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithSi(@RequestParam("code") String code){
        System.out.println("==============================================");
        return ResponseEntity.ok(ms.getEstatesByToggleWithSi(code));
    }

    @GetMapping("/getEstatesByToggleWithGu")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithGu(@RequestParam("code") String code){
        return ResponseEntity.ok(ms.getEstatesByToggleWithGu(code));
    }

    @GetMapping("/getEstatesByToggleWithDong")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithDong(@RequestParam("code") String code){
        return ResponseEntity.ok(ms.getEstatesByToggleWithDong(code));
    }
}
