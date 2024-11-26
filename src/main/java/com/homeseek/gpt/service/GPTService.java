package com.homeseek.gpt.service;

public interface GPTService {
    String getChatCompletion(String userMessage);
//    String getAdvice(String userMessage);
    String getAdvice(String aptName, String si, String gu);
    String crawlHousingMarket();
}
