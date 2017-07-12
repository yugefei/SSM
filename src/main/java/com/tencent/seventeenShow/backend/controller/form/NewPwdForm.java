package com.tencent.seventeenShow.backend.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by EdCho on 2017/5/21 021.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewPwdForm {
    private String originPwd;
    private String newPwd;

    public String getOriginPwd() {
        return originPwd;
    }

    public void setOriginPwd(String originPwd) {
        this.originPwd = originPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
