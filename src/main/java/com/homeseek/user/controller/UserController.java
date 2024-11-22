package com.homeseek.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.homeseek.user.dto.KakaoUserReq;
//import com.homeseek.user.dto.KakaoUserResp;
import com.homeseek.user.dto.UserReq;
import com.homeseek.user.dto.UserResp;
//import com.homeseek.user.service.KakaoService;
import com.homeseek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
//    private final KakaoService kakaoService;
//    private final ObjectMapper objectMapper;

    @PostMapping("/signup")
    public ResponseEntity<UserResp> signUp(@RequestBody UserReq req) {
        return ResponseEntity.ok(userService.signUp(req));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResp> login(@RequestBody UserReq req) {
        return ResponseEntity.ok(userService.login(req));
    }

//    @PostMapping("/kakao/login")
//    public ResponseEntity<KakaoUserResp> kakaoLogin(@RequestBody KakaoUserReq req) {
//        return ResponseEntity.ok(kakaoService.kakaoLogin(req));
//    }
//
//    @PostMapping("/kakao/logout")
//    public ResponseEntity<Void> kakaoLogout(@RequestHeader("Authorization") String accessToken) {
//        kakaoService.logout(accessToken.substring(7));
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResp> getUserInfo(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/oauth")
//    public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
//        log.info("카카오 인증 코드: {}", code);
//
//        try {
//            // 액세스 토큰 받기
//            String tokenResponse = kakaoService.getKakaoAccessToken(code);
//            log.info("카카오 토큰 응답: {}", tokenResponse); // 토큰 응답 로깅 추가
//
//            JsonNode jsonNode = objectMapper.readTree(tokenResponse);
//            String accessToken = jsonNode.get("access_token").asText();
//            log.info("카카오 액세스 토큰: {}", accessToken); // 액세스 토큰 로깅 추가
//
//            // 사용자 프로필 조회
//            KakaoUserResp kakaoUser = kakaoService.getUserProfile(accessToken);
//            log.info("카카오 사용자 정보: {}", kakaoUser); // 사용자 정보 로깅 추가
//
//            // 카카오 정보로 회원가입/로그인 처리
//            KakaoUserReq kakaoUserReq = KakaoUserReq.builder()
//                    .kakaoId(kakaoUser.getKakaoId())
//                    .nickname(kakaoUser.getNickname())
//                    .build();
//
//            try {
//                // 카카오 로그인 처리
//                KakaoUserResp loginResp = kakaoService.kakaoLogin(kakaoUserReq);
//                log.info("로그인 응답: {}", loginResp); // 로그인 응답 로깅 추가
//                return ResponseEntity.ok(loginResp);
//            } catch (Exception e) {
//                log.error("카카오 로그인 처리 중 오류", e);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(e.getMessage());
//            }
//
//        } catch (Exception e) {
//            log.error("카카오 로그인 처리 중 오류", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(e.getMessage());
//        }
//    }
}