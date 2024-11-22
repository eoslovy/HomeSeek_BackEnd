package com.homeseek.user.mapper;

//import com.homeseek.user.dto.KakaoUserReq;
import com.homeseek.user.dto.UserReq;
import com.homeseek.user.dto.UserResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
