package com.tencent.seventeenShow.backend.controller.form;

/**
 * Created by gefeiyu on 2017/7/12.
 */
public class OAuthForm {
    private String checkSum;
    private Long timestamp;
    private String token;
    private String openId;

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
