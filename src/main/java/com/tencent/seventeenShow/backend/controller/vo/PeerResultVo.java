package com.tencent.seventeenShow.backend.controller.vo;

import com.tencent.seventeenShow.backend.model.User;

/**
 * Created by EdwardZhou on 2017/7/15.
 * All Rights Reserved
 */
public class PeerResultVo {
    private User user;
    private Integer roomId;
    private Boolean isRoomAdmin;

    public PeerResultVo(User user, Integer roomId, Boolean isRoomAdmin) {
        this.user = user;
        this.roomId = roomId;
        this.isRoomAdmin = isRoomAdmin;
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

    public Boolean getRoomAdmin() {
        return isRoomAdmin;
    }

    public void setRoomAdmin(Boolean roomAdmin) {
        isRoomAdmin = roomAdmin;
    }
}
