package com.homeseek.trend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrendReq {
    private String startDate;
    private String endDate;
    private String timeUnit;
    private List<KeywordGroup> keywordGroups;

    @Getter
    @Setter
    public static class KeywordGroup {
        private String groupName;
        private List<String> keywords;
    }
}
