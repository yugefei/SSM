package com.tencent.seventeenShow.backend.dao;

import com.tencent.seventeenShow.backend.model.User1;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * Created by gefeiyu on 2017/7/10.
 */
public interface User1Mapper {
    User1 login(@Param("id") String username,@Param("pwd") String pwd);
    int register(@Param("id") User1 user1,@Param("pwd") String pwd);
    int  resetPwd(@Param("id") String username,@Param("pwd") String pwd);
    int changeMobile(@Param("mobile") String mobile ,@Param("userId") Long userId);

    ArrayList<Integer> getBalance(@Param("id") String username);
    ArrayList<String> getFriendsList(@Param("id")String username);

    int extendTime(@Param("id")String username);
    int pairLke(@Param("id")String username);
    int pairDisLike(@Param("id")String username, @Param("lover_name")String lovername);
    int buyFilter(@Param("id")String username);
}
