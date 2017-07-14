package com.tencent.seventeenShow.backend.controller.vo;

import com.tencent.seventeenShow.backend.model.User;

import java.util.ArrayList;

/**
 * Created by gefeiyu on 2017/7/11.
 */
public class FriendsVo {
    private ArrayList<User> friends;

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }
}
