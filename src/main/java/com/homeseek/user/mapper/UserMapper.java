package com.homeseek.user.mapper;

//import com.homeseek.user.dto.KakaoUserReq;
import com.homeseek.map.dto.AptDto;
import com.homeseek.user.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface UserMapper {
    void insertUser(UserReq req);
//    void insertKakaoUser(KakaoUserReq req);
    UserResp selectByUserId(String userId);
//    UserResp selectByKakaoId(String kakaoId);
    UserResp selectById(int id);
    void updateToken(@Param("id") Integer id,
                     @Param("accessToken") String accessToken,
                     @Param("refreshToken") String refreshToken);
    void deleteUser(int id);
    AutoLoginResp checkAutoLogin(@Param("id") String userId, @Param("accessToken") String accessToken);
    void setFavorite(@Param("userId") String userId, @Param("aptSeq") String aptSeq);
    void deleteFavorite(@Param("userId") String userId, @Param("aptSeq") String aptSeq);
    UserFavoirteDto getFavorite(@Param("userId") String userId, @Param("aptSeq") String aptSeq);
    List<AptDto> getFavoriteList(@Param("userId") String userId);
}
