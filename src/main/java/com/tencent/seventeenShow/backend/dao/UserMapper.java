package com.tencent.seventeenShow.backend.dao;
import com.tencent.seventeenShow.backend.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import java.util.ArrayList;
import java.util.Date;
import com.tencent.seventeenShow.backend.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


/**
 * Created by Edward on 2016/9/18.
 */
public interface UserMapper {
//    User login(@Param("openId") String openId);
    User loginByNameAndPwd(@Param("loginName") String loginName, @Param("password") String password);
    int register(@Param("nu") User user,@Param("pwd")String pwd);

    ArrayList<Integer> getBalancesByName(String username);


    int extendTime(@Param("userId") Long userId);
    int clickLike(@Param("userId")Long userId);
    int clickDislike(@Param("userId") Long userId);
    int buyFilter(@Param("userId") Long userId);
    Privilege getPrivilegeByName(@Param("userId") Long userId);
    User getResume(@Param("userId")Long userId);
    ArrayList<User> getFriends(@Param("userId")Long userId);
   // User startMatch(@Param("userId") Long userId);
    int fillInResume(@Param("user")User user);
    int firstLogin(@Param("accessToken") String accessToken, @Param("openId") String openId, @Param("token")String token,
                   @Param("expire")Date expire);
    Token findTokenByOpenId(@Param("openId")String openId);

    String findOpenIdByToken(@Param("token")String token);
    User getResume(@Param("openId")String openId);



    Label getLabel(@Param("token")String token);






    int updateToken(@Param("openId")String openId, @Param("newToken")String newToken, @Param("expire")Date expire);
    int updateExpire(@Param("openId")String openId,@Param("expire")Date expire);

    int studentIdRegistered(@Param("username")String studentId);
    int mobileOccupied(@Param("username")String mobile);
    int setPwd(@Param("id")String mobile,@Param("pwd")String pwd);

    int checkIdAndName(@Param("studentId")String studentId,@Param("name")String name);
    int setAvatar(@Param("avatarUrl")String avatarUrl, @Param("userId")Long userId);
    int setPwdWithOrigin(@Param("oriPwd")String originPwd, @Param("newPwd")String newPwd, @Param("userId")Long userId);

    int rebundingMobile(@Param("mobile")String mobile, @Param("userId")Long userId);
//    Long bindOpenId(@Param("openId") String openId, @Param("userId") Long userId);
//    Long unbunding(@Param("userId") Long userId);
//    Long unbundingByOpenId(@Param("open_id") String openId);


    //开始写接口咯
    int clickDiamond(@Param("openId")String openId);
    int clickLove(@Param("openId")String openId);
    int clickDislike(@Param("openId")String openId);

   // int changeMatch(@Param("openId1")String openId1,@Param("openId2")String openId2);
   // int changeNotMatch(@Param("openId1")String openId1,@Param("openId2")String openId2);

    FriendInfo getFriendInfo(@Param("openId")String openId);
  //  int isMatch(@Param("openId1")String openId1,@Param("openId2")String openId2);

    int addDiamond(@Param("openId")String openId);
}
