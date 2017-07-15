package com.tencent.seventeenShow.backend.service;

import com.tencent.seventeenShow.backend.controller.BaseController;
import com.tencent.seventeenShow.backend.controller.vo.ChangeResumeVo;
import com.tencent.seventeenShow.backend.controller.vo.FriendsVo;
import com.tencent.seventeenShow.backend.mem.Token;
import com.tencent.seventeenShow.backend.model.*;
import com.tencent.seventeenShow.backend.utils.exception.MobileOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentIdOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentNameException;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.tencent.seventeenShow.backend.model.User;
import com.tencent.seventeenShow.backend.utils.exception.MobileOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentIdOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentNameException;
import org.springframework.stereotype.Service;

/**
 * Created by Edward on 2017/2/7 007.
 */

public interface UserService {




    boolean firstLogin(String accessToken, String openId, String token, Date expire);

    com.tencent.seventeenShow.backend.model.Token findTokenByOpenId(String openId);



    Label getLabel(String token);
    //开始写接口咯

    boolean clickDiamond(String openId);
    boolean clickLove(String openId);

   // boolean changeMatch(String openId1,String openId2);
   // boolean changeNotMatch(String openId1,String openId2);
    boolean clickDislike(String openId);
  //  boolean isMatch(String openId1,String openId2);

    FriendInfo getFriendInfo(String openId);

    boolean addDiamond(String openId);
    boolean modifyResume(String openId, ChangeResumeVo changeResumeVo);

    String findOpenIdByToken(String token);
    User getResume(String openId);
    boolean updateToken(String openId,String newToken, Date expire);
    boolean updateExpire(String openId,Date expire);



//commentResult




    boolean userNameTaken(String userName);

    boolean studentRegistered(String studentId);

    boolean resetPwd(String userName, String pwd);

    boolean setAvatar(String avatarUrl, Long userId);

    boolean setPwd(String originPwd, String newPwd, Long userId);

    boolean changeMobile(String mobile, Long userId);
}
