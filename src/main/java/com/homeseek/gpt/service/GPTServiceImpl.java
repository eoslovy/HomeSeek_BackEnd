package com.homeseek.gpt.service;

import com.homeseek.gpt.dto.SearchWordReq;
import com.homeseek.gpt.dto.SearchWordReq.Message;
import com.homeseek.gpt.dto.SearchWordResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GPTServiceImpl implements GPTService {

    private final WebClient webClient;

    public GPTServiceImpl(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public String getChatCompletion(String userMessage) {
        // ChatGPT에 보낼 메시지 설정
        List<Message> messages = List.of(
                new Message("system", "단어에 대해 간단하게 2문장으로 설명해줘"),
                new Message("user", userMessage)
        );

        // OpenAIRequest 생성
        SearchWordReq request = new SearchWordReq("gpt-3.5-turbo", messages, 200, 0.3);

        try {
            // WebClient를 사용해 OpenAI API에 요청
            SearchWordResp response = webClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(SearchWordResp.class)
                    .block();

            // 응답 처리
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                return "No response from OpenAI.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with OpenAI API: " + e.getMessage();
        }
    }
}
