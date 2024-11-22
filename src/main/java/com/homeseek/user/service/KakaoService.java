//package com.homeseek.user.service;
//
//import com.homeseek.user.dto.KakaoUserReq;
//import com.homeseek.user.dto.KakaoUserResp;
//import org.springframework.http.ResponseEntity;
//
//public interface KakaoService {
//    String getAccessToken(String code);
//    KakaoUserResp getUserProfile(String accessToken);
//    KakaoUserResp kakaoLogin(KakaoUserReq req);
//    ResponseEntity<String> logout(String accessToken);
//    String getKakaoAccessToken(String code);
//}