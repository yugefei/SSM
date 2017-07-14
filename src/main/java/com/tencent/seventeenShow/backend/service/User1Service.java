package com.tencent.seventeenShow.backend.service;

import com.tencent.seventeenShow.backend.model.User1;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by gefeiyu on 2017/7/10.
 */
public interface User1Service {
    User1 login(String username,String pwd);
    boolean register(User1 user1,String pwd);
    boolean resetPwd(String username,String pwd);
    boolean changeMobile(String mobile ,Long userId);

    ArrayList<Integer> getBalance(String username);
    ArrayList<String> getFriendsList(String username);

    boolean extendTime(String username);
    boolean pairLke(String username,String lovername);
    boolean pairDisLike(String username);
    boolean buyFilter(String username);

}
