package com.homeseek.crawl.service;

import com.homeseek.crawl.dto.NewsResp;

import java.util.List;

public interface NewsService {
    List<NewsResp> searchNews();
    List<NewsResp> searchNewsRegulation();
}
