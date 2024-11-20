package com.homeseek.gpt.service;

public interface GPTService {
    String getChatCompletion(String userMessage);
    String getAdvice(String userMessage);
}
