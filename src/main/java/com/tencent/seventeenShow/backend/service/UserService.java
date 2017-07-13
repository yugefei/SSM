package com.tencent.seventeenShow.backend.service;

import com.tencent.seventeenShow.backend.model.Privilege;
import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.utils.exception.MobileOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentIdOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentNameException;
import org.apache.ibatis.annotations.Param;
import com.tencent.seventeenShow.backend.model.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2017/2/7 007.
 */

public interface UserService {
    User login(String username, String pwd);

    boolean register(User user,String pwd)  throws StudentIdOccupiedException,MobileOccupiedException,StudentNameException;

    List<Integer> getBalances(String username);
    int extendTime(Long userId);
    boolean clickLike(Long userId);
    boolean clickDislike(Long userId);
    int buyFilter(Long userId);
    Privilege getPrivilegeByName(Long userId);
    User getResume(Long userId);
    ArrayList<User> getFriends(Long userId);
    User startMatch(Long userId);
    boolean fillInResume(User user);

    boolean firstLogin(String accessToken, String openId, String token, long expire);

    Token findTokenByOpenId(String openId);

    boolean updateToken(String openId,String newToken, long expire);
    boolean updateExpire(String openId,long expire);

    boolean userNameTaken(String userName);

    boolean studentRegistered(String studentId);

    boolean resetPwd(String userName, String pwd);

    boolean setAvatar(String avatarUrl, Long userId);

    boolean setPwd(String originPwd, String newPwd, Long userId);

    boolean changeMobile(String mobile, Long userId);
}
