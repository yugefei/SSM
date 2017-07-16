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




    boolean firstLogin(String accessToken, String openId, String token, Date expire, String sig);

    com.tencent.seventeenShow.backend.model.Token findTokenByOpenId(String openId);



    List<String> getLabel(String token);
    //开始写接口咯

    boolean clickDiamond(String openId);
    boolean clickLove(String openId);

   // boolean changeMatch(String openId1,String openId2);
   // boolean changeNotMatch(String openId1,String openId2);
    boolean clickDislike(String openId);
  //  boolean isMatch(String openId1,String openId2);



    boolean addDiamond(String openId,int count);


    String findOpenIdByToken(String token);
    User getResume(String openId);
    boolean updateToken(String openId,String newToken, Date expire);
    boolean updateExpire(String openId,Date expire);

    boolean modifyResume(String openId, ChangeResumeVo changeResumeVo);
//commentResult


    boolean modifyGender(String openId, String gender);

    boolean localMatch(String openId);

    boolean insertLabel(String openId,String[] tag);

    List<User> getFriends(String openId);

    User getFriendInfo(String openId);
    Long getLoveNum(String openId);

    boolean regToHx(String openId);

}
