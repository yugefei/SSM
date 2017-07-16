package com.tencent.seventeenShow.backend.service.impl;

import com.tencent.seventeenShow.backend.conf.GlobalEnv;
import com.tencent.seventeenShow.backend.controller.vo.ChangeResumeVo;
import com.tencent.seventeenShow.backend.dao.UserMapper;
import com.tencent.seventeenShow.backend.model.*;
import com.tencent.seventeenShow.backend.service.UserService;
import com.tencent.seventeenShow.backend.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public User getOtherResume(String openId) {
        return userMapper.getOtherResume(openId);
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

    @Override
    public boolean clickDislike(String openId) {
        return userMapper.clickDislike(openId) >= 1;
    }



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

    @Override
    public boolean regToHx(String openId) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> obj = new HashMap<String, String>();
        obj.put("username", openId);
        obj.put("password", Utils.MD5(openId));
        for(int i = 0; i < 5; ++i){
            try{
                logger.info(String.format("Try %d: OpenId %s",i,openId));
                restTemplate.postForObject(GlobalEnv.hxRoot + "/user",obj,Object.class);
                return true;
            }catch (RestClientException e){
            }
        }

        return false;


    }

    @Override
    public boolean decLove(String openId) {
        return userMapper.decLove(openId) >= 1;
    }

    @Override
    public boolean addRelationShip(String openId1, String openId2) {
        if(userMapper.containRelationship(openId1,openId2) + userMapper.containRelationship(openId2, openId1) >= 1){
            userMapper.addRelationship(openId1, openId2);
            return true;
        }
        return false;
    }
}

