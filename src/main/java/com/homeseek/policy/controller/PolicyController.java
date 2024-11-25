package com.homeseek.policy.controller;

import com.homeseek.policy.service.PolicyCrawlingService;
import com.homeseek.policy.dto.PolicyGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name="정부 정책")
@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyCrawlingService policyCrawlingService;

    @Operation(summary = "규제 불러오기")
    @GetMapping
    public ResponseEntity<Map<String, List<PolicyGroup>>> getPolicyData() {
        List<PolicyGroup> policyGroups = policyCrawlingService.crawlPolicyData();
        return ResponseEntity.ok(Map.of("policies", policyGroups));
    }
}