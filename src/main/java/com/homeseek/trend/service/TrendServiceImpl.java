package com.homeseek.trend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeseek.trend.dto.DistrictReq;
import com.homeseek.trend.dto.TrendReq;
import com.homeseek.trend.dto.TrendResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrendServiceImpl implements TrendService {
    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private final String API_URL = "https://openapi.naver.com/v1/datalab/search";

    @Override
    public TrendResp getTrendData(TrendReq trendReq) {
        if (clientId == null || clientId.isEmpty() || clientSecret == null || clientSecret.isEmpty()) {
            throw new RuntimeException("네이버 API 인증 정보가 올바르게 설정되지 않았습니다.");
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        requestHeaders.put("Content-Type", "application/json");

        String requestBody = convertToJsonString(trendReq);
        String responseData = post(API_URL, requestHeaders, requestBody);
        TrendResp response = convertToTrendResp(responseData);

        if (response.getErrorMessage() != null) {
            throw new RuntimeException("네이버 API 에러: " + response.getErrorMessage());
        }

        return response;
    }

    private TrendResp convertToTrendResp(String jsonResponse) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonResponse, TrendResp.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("응답 JSON 변환 실패", e);
        }
    }

    private String convertToJsonString(TrendReq trendReq) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(trendReq);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("요청 JSON 변환 실패", e);
        }
    }

    private static String post(String apiUrl, Map<String, String> requestHeaders, String requestBody) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    @Override
    public TrendResp getDistrictTrendData(DistrictReq request) {
        // DistrictReq를 TrendReq로 변환
        TrendReq trendReq = convertToTrendReq(request);
        // 기존 getTrendData 메서드 활용
        return getTrendData(trendReq);
    }

    private TrendReq convertToTrendReq(DistrictReq request) {
        TrendReq trendReq = new TrendReq();

        // 날짜 고정 설정
        trendReq.setEndDate("2024-11-15");
        trendReq.setStartDate("2023-11-16");

        // 날짜 설정 (나중에 동적으로 날짜 설정할 때 필요함)
//        String endDate = LocalDate.now();
//        LocalDate startDate = endDate.minusYears(1);
//        trendReq.setEndDate(endDate.format(DateTimeFormatter.ISO_DATE));
//        trendReq.setStartDate(startDate.format(DateTimeFormatter.ISO_DATE));

        // 시간 단위 설정
        trendReq.setTimeUnit("month");

        // 키워드 그룹 생성
        List<TrendReq.KeywordGroup> keywordGroups = request.getDistricts().stream()
                .map(district -> {
                    TrendReq.KeywordGroup group = new TrendReq.KeywordGroup();
                    group.setGroupName(district);
                    group.setKeywords(Arrays.asList(
                            district + " 부동산"
//                            ,
//                            district.replace("구", "") + " 부동산"
                    ));
                    return group;
                })
                .collect(Collectors.toList());

        trendReq.setKeywordGroups(keywordGroups);

        return trendReq;
    }
}