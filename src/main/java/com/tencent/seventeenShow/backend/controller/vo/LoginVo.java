package com.tencent.seventeenShow.backend.controller.vo;

import com.tencent.seventeenShow.backend.model.User;

/**
 * Created by Edward on 2017/2/7 007.
 */
public class LoginVo {
    private String token;
    private boolean singed;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSinged() {
        return singed;
    }

    public void setSinged(boolean singed) {
        this.singed = singed;
    }

    public LoginVo(String token, boolean singed) {
        this.token = token;
        this.singed = singed;
    }
}
