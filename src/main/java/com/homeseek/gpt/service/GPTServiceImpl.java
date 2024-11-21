package com.homeseek.gpt.service;

import com.homeseek.gpt.dto.SearchWordReq;
import com.homeseek.gpt.dto.SearchWordReq.Message;
import com.homeseek.gpt.dto.SearchWordResp;
import com.homeseek.sale.dto.SaleResp;
import com.homeseek.sale.service.SaleService;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Service
public class GPTServiceImpl implements GPTService {

    private final WebClient webClient;
    private final SaleService saleService;
    private static final String KB_URL = "https://data.kbland.kr/kbstats/psychology-of-housing-market";

    private WebClient createWebClient(String apiKey) {
        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public GPTServiceImpl(SaleService saleService, @Value("${openai.api.key}") String apiKey) {
        this.saleService = saleService;
        this.webClient = createWebClient(apiKey);
    }

    @Override
    public String getChatCompletion(String userMessage) {
        // ChatGPT에 보낼 메시지 설정
        List<Message> messages = List.of(
                new Message("system", "단어에 대해 간단하게 2문장으로 설명해줘."),
                new Message("user", userMessage)
        );

        // OpenAIRequest 생성
        SearchWordReq request = new SearchWordReq("gpt-3.5-turbo", messages, 200, 0.3);

        try {
            // WebClient를 사용해 OpenAI API에 요청
            SearchWordResp response = webClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(SearchWordResp.class)
                    .block();

            // 응답 처리
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                return "No response from OpenAI.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with OpenAI API: " + e.getMessage();
        }
    }

    private String crawlHousingMarket() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--blink-settings=imagesEnabled=false");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get(KB_URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(0));
            WebElement txtElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div[data-v-1c68e3e2].txt")));

            return txtElement.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "데이터를 가져오는 중 오류가 발생했습니다: " + e.getMessage();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @Override
    public String getAdvice(String aptName, String si, String gu) {
        String kbresult = crawlHousingMarket();

        // 매물 정보 조회
        List<SaleResp> saleList = saleService.findSalesByAptName(aptName, si, gu);

        // 매물 정보를 문자열로 변환
        StringBuilder saleInfo = new StringBuilder();
        saleInfo.append("\n\n현재 매물 정보:\n");
        for (SaleResp sale : saleList) {
            saleInfo.append(String.format("- %s㎡: %d만원 (%s)\n",
                    sale.getExcluUseAr(),
                    sale.getPrice(),
                    sale.getDescription()
            ));
        }
        System.out.println(saleInfo.length());

        // ChatGPT에 보낼 메시지 설정
        List<Message> messages = List.of(
                new Message("system",
                        "너는 부동산 시장에 대해 조언을 해주는 전문가입니다. " +
                                "현재 시장 상황과 매물 정보를 바탕으로 구매 결정에 대한 조언을 해주세요.\n\n" +
                                "현재 시장 상황:\n" + kbresult +
                                "\n" + saleInfo.toString()
                ),
                new Message("user",
                        String.format("%s %s %s에 있는 아파트를 사려고 하는데 어떨까요?", si, gu, aptName)
                )
        );

        // OpenAIRequest 생성
        SearchWordReq request = new SearchWordReq("gpt-3.5-turbo", messages, 1000, 0.3);

        try {
            // WebClient를 사용해 OpenAI API에 요청
            SearchWordResp response = webClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(SearchWordResp.class)
                    .block();

            // 응답 처리
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                return "No response from OpenAI.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with OpenAI API: " + e.getMessage();
        }
    }
}
