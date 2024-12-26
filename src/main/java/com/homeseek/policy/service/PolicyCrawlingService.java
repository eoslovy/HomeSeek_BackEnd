package com.homeseek.policy.service;

import com.homeseek.policy.dto.PolicyData;
import com.homeseek.policy.dto.PolicyGroup;
import com.homeseek.policy.dto.PolicySubGroup;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PolicyCrawlingService {
    private static final String KB_POLICY_URL = "https://data.kbland.kr/publicdata/real-estate-policy";
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration SCRIPT_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration IMPLICIT_WAIT = Duration.ofSeconds(2);

    public List<PolicyGroup> crawlPolicyData() {
        WebDriver driver = null;
        try {
            driver = initializeDriver();
            configureTimeouts(driver);
            driver.get(KB_POLICY_URL);

            // 필수 요소만 대기
            By containerSelector = By.cssSelector("div[data-v-e213cf2a] .titdepth2");
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(containerSelector));

            // 최소한의 스크롤만 수행
            ((JavascriptExecutor) driver).executeScript(
                    "window.scrollTo(0, Math.min(500, document.body.scrollHeight));"
            );

            return processMainTitles(driver);

        } catch (Exception e) {
            log.error("크롤링 중 오류 발생: ", e);
            throw new RuntimeException("정책 데이터 크롤링 실패", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private WebDriver initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless=new",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--remote-allow-origins=*",
                "--window-size=1920,1080",
                "--disable-extensions",
                "--disable-logging",
                "--disable-notifications",
                "--disable-javascript-harmony-shipping",
                "--disable-dev-tools",
                "--disable-web-security",
                "--disable-client-side-phishing-detection",
                "--disable-default-apps",
                "--disable-prompt-on-repost",
                "--disable-sync",
                "--disable-translate",
                "--disable-hang-monitor",
                "--disable-features=site-per-process"
        );
        return new ChromeDriver(options);
    }

    private void configureTimeouts(WebDriver driver) {
        driver.manage().timeouts()
                .pageLoadTimeout(PAGE_LOAD_TIMEOUT)
                .scriptTimeout(SCRIPT_TIMEOUT)
                .implicitlyWait(IMPLICIT_WAIT);
    }

    private List<PolicyGroup> processMainTitles(WebDriver driver) {
        List<PolicyGroup> policyGroups = new ArrayList<>();
        List<WebElement> mainTitles = driver.findElements(
                By.cssSelector("div[data-v-e213cf2a] .titdepth2")
        );

        log.info("Found {} main titles", mainTitles.size());

        for (WebElement mainTitle : mainTitles) {
            try {
                String groupTitle = mainTitle.getText().trim();
                List<PolicySubGroup> subGroups = "규제지역".equals(groupTitle) ?
                        createRegulationZoneGroups() :
                        processNormalGroups(driver, mainTitle);

                if (!subGroups.isEmpty()) {
                    policyGroups.add(PolicyGroup.builder()
                            .mainTitle(groupTitle)
                            .subGroups(subGroups)
                            .build());
                    log.info("대분류 추출 완료: {}", groupTitle);
                }
            } catch (Exception e) {
                log.warn("대분류 파싱 중 오류: {}", e.getMessage());
            }
        }
        return policyGroups;
    }

    private List<PolicySubGroup> createRegulationZoneGroups() {
        return List.of(
                createRegulationSubGroup("투기지역"),
                createRegulationSubGroup("투기과열지구"),
                createRegulationSubGroup("조정대상지역")
        );
    }

    private PolicySubGroup createRegulationSubGroup(String title) {
        return PolicySubGroup.builder()
                .title(title)
                .policies(List.of(PolicyData.builder()
                        .title("강남 3구(강남·서초·송파) 및 용산구")
                        .details(new ArrayList<>())
                        .build()))
                .build();
    }

    private List<PolicySubGroup> processNormalGroups(WebDriver driver, WebElement mainTitle) {
        List<PolicySubGroup> subGroups = new ArrayList<>();
        try {
            WebElement section = mainTitle.findElement(By.xpath("./following-sibling::div[1]"));
            List<WebElement> subGroupItems = section.findElements(
                    By.cssSelector(".ruletit.type1.colorL, .ruletit.type1.colorR, .ruletit.type1, .ruletit.type2, .ruletit.type3.noicon")
            );

            for (WebElement subGroupItem : subGroupItems) {
                try {
                    String subGroupTitle = subGroupItem.getText().trim();
                    List<PolicyData> policies = new ArrayList<>();

                    WebElement parentElement = subGroupItem.findElement(By.xpath("./.."));
                    List<WebElement> allElements = parentElement.findElements(
                            By.cssSelector(".txt1, .dotlist")
                    );

                    PolicyData currentPolicy = null;
                    List<String> currentDetails = new ArrayList<>();

                    for (WebElement element : allElements) {
                        String className = element.getAttribute("class");

                        if (className.contains("txt1")) {
                            if (currentPolicy != null) {
                                policies.add(currentPolicy);
                            }
                            currentDetails = new ArrayList<>();
                            currentPolicy = PolicyData.builder()
                                    .title(element.getText().trim())
                                    .details(currentDetails)
                                    .build();
                        } else if (className.contains("dotlist")) {
                            currentDetails.add(element.getText().trim());
                        }
                    }

                    if (currentPolicy != null) {
                        policies.add(currentPolicy);
                    }

                    if (!policies.isEmpty()) {
                        PolicySubGroup subGroup = PolicySubGroup.builder()
                                .title(subGroupTitle)
                                .policies(policies)
                                .build();
                        subGroups.add(subGroup);
                        log.info("중분류 추출 완료: {}", subGroupTitle);
                    }
                } catch (Exception e) {
                    log.warn("중분류 파싱 중 오류: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.warn("일반 그룹 처리 중 오류: {}", e.getMessage());
        }
        return subGroups;
    }
}