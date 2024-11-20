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
        System.out.println("=======================동네임즈 응답들옴");
        return ResponseEntity.ok(ms.getDongNames(si, gu));
    }

    @GetMapping("/getGuNames")
    public ResponseEntity<List<GuDto>> getGuNames(@RequestParam("si") String si){
        System.out.println("=======================구네임즈 응답들옴");
        return ResponseEntity.ok(ms.getGuNames(si));
    }

    @GetMapping("/getSiNames")
    public ResponseEntity<List<SiDto>> getSiNames(){
        System.out.println("=======================시네임즈 응답들옴");
        return ResponseEntity.ok(ms.getSiNames());
    }

    @GetMapping("/getEstateByToggleWithSi")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithSi(@RequestParam("si") String si){
        System.out.println("=======================토글토글 응답들옴");
        List<ToggleEstateDto> resp = ms.getEstatesByToggleWithSi(si);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getEstateByToggleWithGu")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithGu(@RequestParam("gu") String gu){
        System.out.println("=======================토글토글 응답들옴");
        List<ToggleEstateDto> resp = ms.getEstatesByToggleWithGu(gu);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getEstateByToggleWithDong")
    public ResponseEntity<List<ToggleEstateDto>> getEstatesByToggleWithDong(@RequestParam("dong") String dong){
        System.out.println("=======================토글토글 응답들옴");
        List<ToggleEstateDto> resp = ms.getEstatesByToggleWithDong(dong);
        return ResponseEntity.ok(resp);
    }
}
