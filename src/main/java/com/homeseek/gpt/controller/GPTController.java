package com.homeseek.gpt.controller;

import com.homeseek.gpt.dto.AdviceReq;
import com.homeseek.gpt.service.GPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openai")
public class GPTController {

    private final GPTService GPTService;

    @Autowired
    public GPTController(GPTService GPTService) {
        this.GPTService = GPTService;
    }

    @PostMapping("/search")
    public ResponseEntity<String> generateText(@RequestBody String userMessage) {
        return ResponseEntity.ok(GPTService.getChatCompletion(userMessage));
    }

    @PostMapping("/advice")
    public String getAdvice(@RequestBody AdviceReq request) {
        return GPTService.getAdvice(
                request.getAptName(),
                request.getSi(),
                request.getGu()
        );
    }
}
