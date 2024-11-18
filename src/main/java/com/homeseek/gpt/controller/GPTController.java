package com.homeseek.gpt.controller;

import com.homeseek.gpt.service.GPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/openai")
public class GPTController {

    private final GPTService GPTService;

    @Autowired
    public GPTController(GPTService GPTService) {
        this.GPTService = GPTService;
    }

    @PostMapping("/search")
    public String generateText(@RequestBody String userMessage) {
        return GPTService.getChatCompletion(userMessage);
    }
}
