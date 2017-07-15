package com.tencent.seventeenShow.backend.model;

import java.util.Date;

/**
 * Created by gefeiyu on 2017/7/12.
 */
public class Token {
    private String token;
    private Date expire;
    private String accessToken;
    private String sig;




    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
