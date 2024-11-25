package com.homeseek.policy.controller;

import com.homeseek.policy.service.PolicyCrawlingService;
import com.homeseek.policy.dto.PolicyGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyCrawlingService policyCrawlingService;

    @GetMapping
    public ResponseEntity<Map<String, List<PolicyGroup>>> getPolicyData() {
        List<PolicyGroup> policyGroups = policyCrawlingService.crawlPolicyData();
        return ResponseEntity.ok(Map.of("policies", policyGroups));
    }
}