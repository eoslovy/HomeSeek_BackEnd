package com.homeseek.crawl.controller;

import com.homeseek.crawl.dto.NewsResp;
import com.homeseek.crawl.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crawl")
@RequiredArgsConstructor
public class CrawlController {
    private final NewsService newsService;

    @GetMapping("/searchNews")
    public ResponseEntity<List<NewsResp>> searchNews() {
        List<NewsResp> newsList = newsService.searchNews();
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/searchNewsRegulation")
    public ResponseEntity<List<NewsResp>> searchNewsRegulation() {
        List<NewsResp> newsList = newsService.searchNewsRegulation();
        return ResponseEntity.ok(newsList);
    }
}
