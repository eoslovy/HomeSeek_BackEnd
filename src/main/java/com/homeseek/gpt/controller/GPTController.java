package com.homeseek.gpt.controller;

import com.homeseek.gpt.dto.AdviceReq;
import com.homeseek.gpt.service.GPTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="GPT")
@RestController
@RequestMapping("/openai")
public class GPTController {

    private final GPTService GPTService;

    @Autowired
    public GPTController(GPTService GPTService) {
        this.GPTService = GPTService;
    }

    @Operation(summary = "검색 기능")
    @PostMapping("/search")
    public ResponseEntity<String> generateText(@RequestBody String userMessage) {
        return ResponseEntity.ok(GPTService.getChatCompletion(userMessage));
    }

    @Operation(summary = "추천 기능")
    @PostMapping("/advice")
    public String getAdvice(@RequestBody AdviceReq request) {
        return GPTService.getAdvice(
                request.getAptName(),
                request.getSi(),
                request.getGu()
        );
    }

    @Operation(summary = "시장 심리 지수")
    @GetMapping("/sentiment")
    public ResponseEntity<String> getSentiment() {
        return ResponseEntity.ok(GPTService.crawlHousingMarket());
    }
}
