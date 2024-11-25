package com.homeseek.policy.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyData {
    private String title;      // txt1 제목
    private List<String> details;  // dotlist 내용들
}
