package com.homeseek.user.service;

import com.homeseek.user.dto.UserReq;
import com.homeseek.user.dto.UserResp;

public interface UserService {
    UserResp signUp(UserReq req);
    UserResp login(UserReq req);
    UserResp getUserInfo(int id);
    void deleteUser(int id);
}
