package com.tencent.seventeenShow.backend.controller.vo;

import com.tencent.seventeenShow.backend.model.User;

/**
 * Created by EdwardZhou on 2017/7/15.
 * All Rights Reserved
 */
public class PeerResultVo {
    private User user;
    private Integer roomId;

    public PeerResultVo(User user, Integer roomId) {
        this.user = user;
        this.roomId = roomId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
