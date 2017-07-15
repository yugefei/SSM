package com.tencent.seventeenShow.backend.service.impl;

import com.tencent.seventeenShow.backend.controller.vo.ChangeResumeVo;
import com.tencent.seventeenShow.backend.dao.UserMapper;
import com.tencent.seventeenShow.backend.model.*;
import com.tencent.seventeenShow.backend.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


/**
 * Created by Edward on 2017/2/7 007.
 */
@Service("UserService")
public class UserServiceImpl  implements UserService{

    private Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;



    @Override
    public boolean firstLogin(String accessToken, String openId, String token, Date expire, String sig) {
        if(userMapper.firstLogin(accessToken,openId,token,expire,sig)>=1)
            return true;
        return false;
    }



    @Override
    public com.tencent.seventeenShow.backend.model.Token findTokenByOpenId(String openId) {
        return userMapper.findTokenByOpenId(openId);
    }

    @Override
    public List<String> getLabel(String token) {
        return userMapper.getLabel(token);
    }



    @Override
    public String findOpenIdByToken(String token) {
        return userMapper.findOpenIdByToken(token);
    }

    @Override
    public User getResume(String openId) {
        return userMapper.getResume(openId);
    }

    @Override
    public boolean updateToken(String openId, String newToken, Date expire) {
        if(userMapper.updateToken(openId,newToken,expire)>=1)
            return true;
        return false;
    }

    @Override
    public boolean updateExpire(String openId, Date expire) {
        if(userMapper.updateExpire(openId,expire)>=1)
            return true;
        return false;
    }




//开始写接口咯
    @Override
    public boolean clickDiamond(String openId) {
        if(userMapper.clickDiamond(openId)>=1)
            return true;
        return false;
    }

    @Override
    public boolean clickLove(String openId) {
        if(userMapper.clickLove(openId)>=1)
            return true;
        return false;
    }
/*
    @Override
    public boolean changeMatch(String openId1, String openId2) {
        if(userMapper.changeMatch(openId1,openId2)>=1)
            return true;
        return false;
    }

    @Override
    public boolean changeNotMatch(String openId1, String openId2) {
        if(userMapper.changeNotMatch(openId1,openId2)>=1)
            return true;
        return false;
    }
*/
    @Override
    public boolean clickDislike(String openId) {
        if(userMapper.clickDislike(openId)>=1)
            return true;
        return false;
    }
/*
    @Override
    public boolean isMatch(String openId1, String openId2) {
        if(userMapper.isMatch(openId1,openId2)==2)
            return true;
        return false;
    }
*/


    @Override
    public boolean addDiamond(String openId,int count) {
        return userMapper.addDiamond(openId,count)>=1;
    }



    @Override
    public boolean modifyGender(String openId, String gender) {
        return userMapper.modifyGender(openId,gender)>=1;
    }

    @Override
    public boolean localMatch(String openId) {
        return userMapper.localMatch(openId)==1;
    }


    @Override
    public boolean insertLabel(String openId, String[] tag) {
        userMapper.deleteLabel(openId);
        return userMapper.insertLabel(openId,tag)>=1;
    }


    @Override
    public boolean modifyResume(String openId, ChangeResumeVo changeResumeVo) {
        if(userMapper.resumeExists(openId)>=1){
            return userMapper.modifyResume(openId,changeResumeVo) >= 1;
        }
        return userMapper.insertResume(openId,changeResumeVo)>=1;
    }

    @Override
    public List<User> getFriends(String openId) {
        return userMapper.getFriends(openId);
    }

    @Override
    public User getFriendInfo(String openId) {
        return userMapper.getFriendInfo(openId);
    }

    @Override
    public Long getLoveNum(String openId) {
        return userMapper.getLoveNum(openId);
    }


}




