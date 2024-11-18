package com.homeseek.crawl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResp {
    private String title;
    private String originalLink;
    private String link;
    private String description;
    private String pubDate;
}
