package com.tencent.seventeenShow.backend.dao;
import com.tencent.seventeenShow.backend.controller.vo.ChangeResumeVo;
import com.tencent.seventeenShow.backend.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tencent.seventeenShow.backend.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


/**
 * Created by Edward on 2016/9/18.
 */
public interface UserMapper {

    int firstLogin(@Param("accessToken") String accessToken, @Param("openId") String openId, @Param("token")String token,
                   @Param("expire")Date expire, @Param("sig")String sig);

    Token findTokenByOpenId(@Param("openId")String openId);

    String findOpenIdByToken(@Param("token")String token);

    User getResume(@Param("openId")String openId);

    User getOtherResume(@Param("openId")String openId);



    List<String> getLabel(@Param("openId")String openId);

    int updateToken(@Param("openId")String openId, @Param("newToken")String newToken, @Param("expire")Date expire);

    int updateExpire(@Param("openId")String openId,@Param("expire")Date expire);

    int clickDiamond(@Param("openId")String openId);

    int clickLove(@Param("openId")String openId);

    int clickDislike(@Param("openId")String openId);

    int addDiamond(@Param("openId")String openId,@Param("count")int count);

    int resumeExists(@Param("openId")String openId);

    int insertResume(@Param("openId")String openId, @Param("vo")ChangeResumeVo changeResumeVo);

    int modifyResume(@Param("openId")String openId ,@Param("vo")ChangeResumeVo changeResumeVo);

    int modifyGender(@Param("openId")String openId,@Param("gender") String gender);

    int localMatch(@Param("openId")String openId);

    int deleteLabel(@Param("openId")String openId);

    int insertLabel(@Param("openId")String openId,@Param("labels")String[] labels);

    List<User> getFriends(@Param("openId")String openId);

    User getFriendInfo(@Param("openId")String openId);

    int decLove(@Param("openId")String openId);

    Long getLoveNum(@Param("openId")String openId);

}
