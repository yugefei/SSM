package com.tencent.seventeenShow.backend.controller.vo;

import java.util.Date;

/**
 * Created by Edward on 2017/3/31 031.
 */
public class UploadTokenVo {
    private String token;
    private Date expireAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
}
