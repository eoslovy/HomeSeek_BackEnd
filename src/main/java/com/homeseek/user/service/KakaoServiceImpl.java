//package com.homeseek.user.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.homeseek.config.JwtUtil;
//import com.homeseek.user.dto.KakaoUserReq;
//import com.homeseek.user.dto.KakaoUserResp;
//import com.homeseek.user.dto.UserResp;
//import com.homeseek.user.mapper.UserMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Collections;
//import org.springframework.http.HttpHeaders;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//@Slf4j
//public class KakaoServiceImpl implements KakaoService {
//
//    @Value("${kakao.client.id}")
//    private String clientId;
//
//    @Value("${kakao.client.secret}")
//    private String clientSecret;
//
//    @Value("${kakao.redirect.uri}")
//    private String redirectUri;
//
//    private final UserMapper userMapper;
//    private final JwtUtil jwtUtil;
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public KakaoUserResp kakaoLogin(KakaoUserReq req) {
//        // 기존 카카오 사용자 확인
//        UserResp existingUser = userMapper.selectByKakaoId(req.getKakaoId());
//
//        if (existingUser != null) {
//            // 기존 사용자인 경우 토큰 업데이트
//            String accessToken = jwtUtil.createAccessToken(req.getKakaoId());
//            String refreshToken = jwtUtil.createRefreshToken();
//
//            userMapper.updateToken(existingUser.getId(), accessToken, refreshToken);
//
//            return KakaoUserResp.builder()
//                    .id(existingUser.getId())
//                    .kakaoId(req.getKakaoId())
//                    .nickname(existingUser.getNickname())
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .build();
//        } else {
//            // 새로운 카카오 사용자 등록
//            String accessToken = jwtUtil.createAccessToken(req.getKakaoId());
//            String refreshToken = jwtUtil.createRefreshToken();
//
//            // 새로운 사용자 등록
//            userMapper.insertKakaoUser(req);
//
//            // 토큰 업데이트
//            UserResp newUser = userMapper.selectByKakaoId(req.getKakaoId());
//            userMapper.updateToken(newUser.getId(), accessToken, refreshToken);
//
//            return KakaoUserResp.builder()
//                    .id(newUser.getId())
//                    .kakaoId(req.getKakaoId())
//                    .nickname(newUser.getNickname())
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .build();
//        }
//    }
//
//    @Override
//    public String getAccessToken(String code) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("grant_type", "authorization_code");
//            params.add("client_id", clientId);
//            params.add("redirect_uri", redirectUri);
//            params.add("code", code);
//
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(
//                    "https://kauth.kakao.com/oauth/token",
//                    HttpMethod.POST,
//                    request,
//                    String.class
//            );
//
//            JsonNode responseNode = objectMapper.readTree(response.getBody());
//            return responseNode.get("access_token").asText();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to get access token from Kakao", e);
//        }
//    }
//
//    @Override
//    public KakaoUserResp getUserProfile(String accessToken) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + accessToken);
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//            HttpEntity<String> request = new HttpEntity<>(headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(
//                    "https://kapi.kakao.com/v2/user/me",
//                    HttpMethod.GET,
//                    request,
//                    String.class
//            );
//
//            log.info("카카오 API 응답: {}", response.getBody());
//
//            JsonNode userProfile = objectMapper.readTree(response.getBody());
//            Long kakaoId = userProfile.get("id").asLong();
//
//            // properties와 kakao_account에서 정보 추출
//            JsonNode properties = userProfile.path("properties");
//            String nickname = properties.path("nickname").asText("기본 닉네임");
//
//            log.info("카카오 ID: {}, 닉네임: {}", kakaoId, nickname);
//
//            return KakaoUserResp.builder()
//                    .kakaoId(String.valueOf(kakaoId))
//                    .nickname(nickname)
//                    .build();
//        } catch (Exception e) {
//            log.error("카카오 프로필 조회 실패: {}", e.getMessage(), e);
//            throw new RuntimeException("카카오 프로필 조회 실패", e);
//        }
//    }
//
//    @Override
//    public ResponseEntity<String> logout(String accessToken) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + accessToken);
//
//            HttpEntity<String> request = new HttpEntity<>(headers);
//
//            return restTemplate.exchange(
//                    "https://kapi.kakao.com/v1/user/logout",
//                    HttpMethod.POST,
//                    request,
//                    String.class
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to logout from Kakao", e);
//        }
//    }
//
//    private KakaoUserResp toResponse(JsonNode userProfile) {
//        JsonNode kakaoAccountNode = userProfile.get("kakao_account");
//        JsonNode profileNode = kakaoAccountNode.get("profile");
//
//        String profileNickname = profileNode.get("nickname").asText();
//        String kakaoId = userProfile.get("id").asText();
//
//        // 토큰 생성
//        String accessToken = jwtUtil.createAccessToken(kakaoId);
//        String refreshToken = jwtUtil.createRefreshToken();
//
//        return KakaoUserResp.builder()
//                .kakaoId(kakaoId)
//                .nickname(profileNickname)
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    public String getKakaoAccessToken(String code) {
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//
//        try {
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            StringBuilder sb = new StringBuilder();
//            sb.append("grant_type=authorization_code");
//            sb.append("&client_id=" + clientId);
//            sb.append("&client_secret=" + clientSecret);
//            sb.append("&redirect_uri=" + redirectUri);
//            sb.append("&code=" + code);
//            bw.write(sb.toString());
//            bw.flush();
//
//            int responseCode = conn.getResponseCode();
//            log.info("responseCode : " + responseCode);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            String result = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            log.info("response body : " + result);
//
//            br.close();
//            bw.close();
//
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("카카오 토큰 받기 실패", e);
//        }
//    }
//}