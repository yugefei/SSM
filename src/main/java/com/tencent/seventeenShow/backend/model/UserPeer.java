package com.tencent.seventeenShow.backend.model;

/**
 * Created by EdwardZhou on 2017/7/15.
 * All Rights Reserved
 */
public class UserPeer {
    private User a;
    private User b;
    private Integer roomNumber;

    public UserPeer(User a, User b, Integer roomNumber) {
        this.a = a;
        this.b = b;
        this.roomNumber = roomNumber;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public User getPeer(String openId) throws NullPointerException{
        if(openId == null)
            throw new NullPointerException("openId should not be null");

        if(openId.equals(a.getOpenId())){
            return b;
        }else if (openId.equals(b.getOpenId())){
            return a;
        }else{
            throw new NullPointerException("the user is not in peer");
        }
    }

}
