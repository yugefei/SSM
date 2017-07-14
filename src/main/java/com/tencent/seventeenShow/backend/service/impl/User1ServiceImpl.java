package com.tencent.seventeenShow.backend.service.impl;

import com.tencent.seventeenShow.backend.dao.User1Mapper;
import com.tencent.seventeenShow.backend.model.User1;
import com.tencent.seventeenShow.backend.service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Created by gefeiyu on 2017/7/10.
 */
@Service("user1Service")
public class User1ServiceImpl implements User1Service {
    private Logger logger = Logger.getLogger(User1ServiceImpl.class);
    @Autowired
    private User1Mapper user1Mapper;

    @Override
    public User1 login(String username, String pwd) {
        return user1Mapper.login(username,pwd);
    }

    @Override
    public boolean register(User1 user1, String pwd) {
        return false;
    }

    @Override
    public boolean resetPwd(String username, String pwd) {
        return false;
    }

    @Override
    public boolean changeMobile(String mobile, Long userId) {
        return false;
    }

    @Override
    public ArrayList<Integer> getBalance(String username) {
        return null;
    }

    @Override
    public ArrayList<String> getFriendsList(String username) {
        return null;
    }

    @Override
    public boolean extendTime(String username) {
        return false;
    }

    @Override
    public boolean pairLke(String username, String lovername) {
        return false;
    }

    @Override
    public boolean pairDisLike(String username) {
        return false;
    }

    @Override
    public boolean buyFilter(String username) {
        return false;
    }
}
