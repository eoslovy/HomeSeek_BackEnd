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
public class PolicyGroup {
    private String mainTitle;           // titdepth2 (대분류)
    private List<PolicySubGroup> subGroups;  // ruletit type1, type2 (중분류)
}
