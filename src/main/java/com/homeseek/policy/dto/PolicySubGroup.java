package com.homeseek.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicySubGroup {
    private String title;       // ruletit type1, type2 제목
    private List<PolicyData> policies;  // txt1과 dotlist를 포함하는 정책 데이터
}
