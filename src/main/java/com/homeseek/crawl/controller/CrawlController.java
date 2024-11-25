package com.homeseek.crawl.controller;

import com.homeseek.crawl.dto.NewsResp;
import com.homeseek.crawl.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="뉴스")
@RestController
@RequestMapping("/crawl")
@RequiredArgsConstructor
public class CrawlController {
    private final NewsService newsService;

    @Operation(summary = "부동산 뉴스")
    @GetMapping("/searchNews")
    public ResponseEntity<List<NewsResp>> searchNews() {
        List<NewsResp> newsList = newsService.searchNews();
        return ResponseEntity.ok(newsList);
    }

    @Operation(summary = "부동산 규제 뉴스")
    @GetMapping("/searchNewsRegulation")
    public ResponseEntity<List<NewsResp>> searchNewsRegulation() {
        List<NewsResp> newsList = newsService.searchNewsRegulation();
        return ResponseEntity.ok(newsList);
    }
}
