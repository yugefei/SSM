package com.tencent.seventeenShow.backend.mem;

import java.util.Date;

/**
 * Created by gefeiyu on 2017/7/12.
 */
public class Token {
    private String accessToken;
    private String openId;
    private Date exipreTime;

    public Date getExipreTime() {
        return exipreTime;
    }

    public void setExipreTime(Date exipreTime) {
        this.exipreTime = exipreTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
