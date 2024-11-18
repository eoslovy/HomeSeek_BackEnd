package com.homeseek.crawl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeseek.crawl.dto.NewsResp;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService{
    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private static final int FIXED_DISPLAY = 20;
    private static final int FIXED_START = 1;

    @Override
    public List<NewsResp> searchNews() {
        String apiUrl = createApiUrl();
        String jsonResponse = callNaverApi(apiUrl);
        return parseJsonResponse(jsonResponse);
    }

    @Override
    public List<NewsResp> searchNewsRegulation() {
        String apiUrl = createApiRegulationUrl();
        String jsonResponse = callNaverApi(apiUrl);
        return parseJsonResponse(jsonResponse);
    }

    private String createApiUrl() {
        try {
            String encodedQuery = URLEncoder.encode("부동산", "UTF-8");
            return String.format(
                    "https://openapi.naver.com/v1/search/news?query=%s&display=%d&start=%d&sort=sim",
                    encodedQuery, FIXED_DISPLAY, FIXED_START
            );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }
    }

    private String createApiRegulationUrl() {
        try {
            String encodedQuery = URLEncoder.encode("부동산 규제", "UTF-8");
            return String.format(
                    "https://openapi.naver.com/v1/search/news?query=%s&display=%d&start=%d&sort=sim",
                    encodedQuery, FIXED_DISPLAY, FIXED_START
            );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }
    }

    private String callNaverApi(String apiUrl) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                throw new RuntimeException("API 호출 실패: " + readBody(con.getErrorStream()));
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) {
        try (BufferedReader lineReader = new BufferedReader(new InputStreamReader(body))) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    private List<NewsResp> parseJsonResponse(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode items = root.get("items");

            List<NewsResp> newsList = new ArrayList<>();

            for (JsonNode item : items) {
                NewsResp news = new NewsResp(
                        removeHtmlTags(item.get("title").asText()),
                        item.get("originallink").asText(),
                        item.get("link").asText(),
                        removeHtmlTags(item.get("description").asText()),
                        item.get("pubDate").asText()
                );
                newsList.add(news);
            }

            return newsList;
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }

    private String removeHtmlTags(String text) {
        String noTags = text.replaceAll("<[^>]*>", "");
        return StringEscapeUtils.unescapeHtml4(noTags);
    }
}
