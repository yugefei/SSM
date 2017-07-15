package com.tencent.seventeenShow.backend.service.impl;

import com.tencent.seventeenShow.backend.controller.vo.ChangeResumeVo;
import com.tencent.seventeenShow.backend.dao.UserMapper;
import com.tencent.seventeenShow.backend.model.*;
import com.tencent.seventeenShow.backend.service.UserService;
import com.tencent.seventeenShow.backend.utils.exception.MobileOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentIdOccupiedException;
import com.tencent.seventeenShow.backend.utils.exception.StudentNameException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public User login(String username, String pwd){
        return userMapper.loginByNameAndPwd(username,pwd);
    }

    @Override
    public boolean register(User user,String pwd) throws StudentIdOccupiedException,MobileOccupiedException,StudentNameException{
        if(userMapper.studentIdRegistered(user.getUsername()) >= 1){
            throw new StudentIdOccupiedException();
        }
        if(userMapper.mobileOccupied(user.getMobile()) >= 1){
            throw new MobileOccupiedException();
        }

        return userMapper.register(user,pwd) == 1;
    }

    @Override
    public List<Integer> getBalances(String username) {
        return userMapper.getBalancesByName(username);
    }

    @Override
    public int extendTime(Long userId) {
        return userMapper.extendTime(userId);
    }


    @Override
    public boolean clickLike(Long userId) {
        if(userMapper.clickLike(userId)>=1)
            return true;
     return false;
    }


    @Override
    public boolean clickDislike(Long userId) {
        if(userMapper.clickDislike(userId)>=1)
            return true;
        return false;
    }

    @Override
    public int buyFilter(Long userId) {
        return userMapper.buyFilter(userId);
    }

    @Override
    public Privilege getPrivilegeByName(Long userId) {
        return userMapper.getPrivilegeByName(userId);
    }




    @Override
    public ArrayList<User> getFriends(Long userId) {
        return userMapper.getFriends(userId);
    }
/*
    @Override
    public User startMatch(Long userId) {
        return userMapper.startMatch(userId);
    }
*/
    @Override
    public boolean fillInResume(User user) {
        if (userMapper.fillInResume(user)>=1)
            return true;
        return  false;
    }

    @Override
    public boolean firstLogin(String accessToken, String openId, String token, Date expire) {
        if(userMapper.firstLogin(accessToken,openId,token,expire)>=1)
            return true;
        return false;
    }



    @Override
    public com.tencent.seventeenShow.backend.model.Token findTokenByOpenId(String openId) {
        return userMapper.findTokenByOpenId(openId);
    }

    @Override
    public Label getLabel(String token) {
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


    @Override
    public boolean userNameTaken(String userName) {
        return userMapper.mobileOccupied(userName) >= 1;
    }

    @Override
    public boolean resetPwd(String userId, String pwd) {
        return userMapper.setPwd(userId,pwd) >= 1;
    }

    @Override
    public boolean studentRegistered(String studentId) {
        return userMapper.studentIdRegistered(studentId) >= 1;
    }

    @Override
    public boolean setAvatar(String avatarUrl, Long userId) {
        return userMapper.setAvatar(avatarUrl, userId) == 1;
    }

    @Override
    public boolean setPwd(String originPwd, String newPwd, Long userId) {
        return userMapper.setPwdWithOrigin(originPwd, newPwd, userId) == 1;
    }

    @Override
    public boolean changeMobile(String mobile, Long userId) {
        return userMapper.rebundingMobile(mobile, userId) == 1;
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
    public FriendInfo getFriendInfo(String openId) {
        return userMapper.getFriendInfo(openId);
    }

    @Override
    public boolean addDiamond(String openId) {
        return false;
    }

    @Override
    public boolean modifyResume(String openId, ChangeResumeVo changeResumeVo) {
        return false;
    }


}




