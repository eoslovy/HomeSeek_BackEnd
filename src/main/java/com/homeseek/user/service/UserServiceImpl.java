package com.homeseek.user.service;

import com.homeseek.config.JwtUtil;
import com.homeseek.user.dto.UserReq;
import com.homeseek.user.dto.UserResp;
import com.homeseek.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResp signUp(UserReq req) {
        // 사용자 중복 검사
        if (userMapper.selectByUserId(req.getUserId()) != null) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(req.getPw());

        // 토큰 생성
        String accessToken = jwtUtil.createAccessToken(req.getUserId());
        String refreshToken = jwtUtil.createRefreshToken();

        // 새로운 UserReq 객체 생성
        UserReq newUser = UserReq.builder()
                .userId(req.getUserId())
                .pw(encodedPassword)
                .nickname(req.getNickname())
                .build();

        log.debug("newUser.getUserId(): {}", newUser.getUserId());
        // 사용자 등록
        userMapper.insertUser(newUser);

        // 토큰 업데이트
        userMapper.updateToken(newUser.getId(), accessToken, refreshToken);

        // 응답 생성
        return UserResp.builder()
                .id(newUser.getId())
                .userId(newUser.getUserId())
                .nickname(newUser.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserResp login(UserReq req) {
        // 사용자 조회
        UserResp user = userMapper.selectByUserId(req.getUserId());  // UserResp로 받기
        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(req.getPw(), user.getPw())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        String accessToken = jwtUtil.createAccessToken(user.getUserId());
        String refreshToken = jwtUtil.createRefreshToken();

        // 토큰 업데이트
        userMapper.updateToken(user.getId(), accessToken, refreshToken);

        // 응답 생성
        return UserResp.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResp getUserInfo(int id) {
        UserResp user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public void deleteUser(int id) {
        if (userMapper.selectById(id) == null) {
            throw new RuntimeException("User not found");
        }
        userMapper.deleteUser(id);
    }
}
