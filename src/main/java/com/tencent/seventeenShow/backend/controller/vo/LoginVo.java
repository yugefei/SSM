package com.tencent.seventeenShow.backend.controller.vo;

import com.tencent.seventeenShow.backend.model.User;

/**
 * Created by Edward on 2017/2/7 007.
 */
public class LoginVo {
    private String token;
    private Boolean signed;

    public LoginVo(String token, Boolean signed){
        this.token = token;
        this.signed = signed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }
}
