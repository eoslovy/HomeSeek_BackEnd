package com.homeseek.gpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchWordResp {

    @JsonProperty("choices")
    private List<Choice> choices;

    @Getter
    @Setter
    public static class Choice {
        @JsonProperty("message")
        private Message message;
    }

    @Getter
    @Setter
    public static class Message {
        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;
    }
}
