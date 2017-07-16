package com.tencent.seventeenShow.backend.model;

import com.tencent.seventeenShow.backend.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by EdwardZhou on 2017/7/15.
 * All Rights Reserved
 */
public class UserPeer {
    @Autowired
    private UserMapper userMapper;

    private static int kUSER_CLICK_LIKE = 1;
    private static int kUSER_CLICK_DISLIKE = 2;
    private static int kUSER_UNCLICK = 0;

    public static int kMATCHED = 1;
    public static int kUNMATCHED = 2;
    public static int kRESULT_UNKNOWN = 0;

    private User a;
    private User b;
    private int aClickResult = kUSER_UNCLICK;
    private int bClickResult = kUSER_UNCLICK;

    private boolean aResultPublished = false;
    private boolean bResultPublished = false;

    private boolean canDelete = false;

    private Integer roomNumber;

    private Integer totalSeconds; //默认17s

    public UserPeer(User a, User b, Integer roomNumber) {
        this.a = a;
        this.b = b;
        this.roomNumber = roomNumber;
        totalSeconds = 17;
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

    public User getA() {
        return a;
    }

    public User getB() {
        return b;
    }

    public void clickLike(String openId){
        if(openId == null)
            throw new NullPointerException("openId should not be null");

        if(openId.equals(a.getOpenId())){
            if(aClickResult != kUSER_CLICK_LIKE)
                userMapper.decLove(a.getOpenId());

            aClickResult = kUSER_CLICK_LIKE;
        }else if (openId.equals(b.getOpenId())){
            if(bClickResult != kUSER_CLICK_LIKE)
                userMapper.decLove(b.getOpenId());

            bClickResult = kUSER_CLICK_LIKE;
        }else{
            throw new NullPointerException("the user is not in peer");
        }
    }

    public void clickDislike(String openId){
        if(openId == null)
            throw new NullPointerException("openId should not be null");

        if(openId.equals(a.getOpenId())){
            aClickResult = kUSER_CLICK_DISLIKE;
        }else if (openId.equals(b.getOpenId())){
            bClickResult = kUSER_CLICK_DISLIKE;
        }else{
            throw new NullPointerException("the user is not in peer");
        }
    }

    public int matchResult(String openId){
        if(aClickResult == kUSER_CLICK_LIKE && bClickResult ==kUSER_CLICK_LIKE){
            this.setResultPublished(openId);
            return kMATCHED;
        }


        if(aClickResult != kUSER_UNCLICK && bClickResult != kUSER_UNCLICK){
            this.setResultPublished(openId);
            return kUNMATCHED;
        }

        return kRESULT_UNKNOWN;
    }

    private void setResultPublished(String openId){
        if(openId == null)
            throw new NullPointerException("openId should not be null");

        if(openId.equals(a.getOpenId())){
            aResultPublished = true;
        }else if (openId.equals(b.getOpenId())){
            bResultPublished = true;
        }else{
            throw new NullPointerException("the user is not in peer");
        }
    }

    public boolean isCanDelete() {
        return aResultPublished && bResultPublished;
    }

    public Integer getTotalSeconds() {
        return this.totalSeconds;
    }

    public void setTotalSeconds(Integer totalSeconds) {
        this.totalSeconds = totalSeconds;
    }
}
