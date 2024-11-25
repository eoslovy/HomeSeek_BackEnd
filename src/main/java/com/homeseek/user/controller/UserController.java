package com.homeseek.user.controller;


import com.homeseek.map.dto.AptDto;
import com.homeseek.user.dto.*;
import com.homeseek.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name="유저")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
//    private final KakaoService kakaoService;
//    private final ObjectMapper objectMapper;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserResp> signUp(@RequestBody UserReq req) {
        return ResponseEntity.ok(userService.signUp(req));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserReq req) {
        try {
            UserResp resp = userService.login(req);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkAutoLogin")
    public ResponseEntity<AutoLoginResp> checkAutoLogin(@RequestParam AutoLoginReq req) {
        return ResponseEntity.ok(userService.checkAutoLogin(req));
    }

    @Operation(summary = "아이디 중복 조회")
    @GetMapping("/check-id/{userId}")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateId(@PathVariable String userId) {
        boolean isAvailable = userService.checkDuplicateId(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
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

    @Operation(summary = "유저 조회")
    @GetMapping("/{id}")
    public ResponseEntity<UserResp> getUserInfo(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @Operation(summary = "유저 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/setFavorite")
    public ResponseEntity<Void> setFavorite(@RequestBody UserFavoirteReq req) {
        System.out.println("========================응답 들옴?");
        System.out.println(req.getUserId());
        System.out.println(req.getAptSeq());
        userService.setFavorite(req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteFavorite")
    public ResponseEntity<Void> deleteFavorite(@RequestBody UserFavoirteReq req) {
        userService.deleteFavorite(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFavorite")
    public ResponseEntity<UserFavoirteResp> getFavorite(@RequestParam("userId") String userId, @RequestParam("aptSeq") String aptSeq) {
        return ResponseEntity.ok(userService.getFavorite(userId, aptSeq));
    }

    @GetMapping("/getFavoriteList")
    public ResponseEntity<List<AptDto>> getFavoriteList(@RequestParam("userId") String userId){
        return ResponseEntity.ok(userService.getFavoriteList(userId));
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