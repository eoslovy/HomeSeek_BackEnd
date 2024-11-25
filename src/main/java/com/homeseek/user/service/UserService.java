package com.homeseek.user.service;

import com.homeseek.map.dto.AptDto;
import com.homeseek.user.dto.*;

import java.util.List;

public interface UserService {
    UserResp signUp(UserReq req);
    UserResp login(UserReq req);
    UserResp getUserInfo(int id);
    void deleteUser(int id);
    boolean checkDuplicateId(String userId);
    AutoLoginResp checkAutoLogin(AutoLoginReq req);
    void setFavorite(UserFavoirteReq req);
    void deleteFavorite(UserFavoirteReq req);
    UserFavoirteResp getFavorite(String userId, String aptSeq);
    List<AptDto> getFavoriteList(String userId);
}
