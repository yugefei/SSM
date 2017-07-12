package com.tencent.seventeenShow.backend.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Edward on 2017/2/7 007.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class LoginForm {
    private String openId;
    private String accessToken;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
