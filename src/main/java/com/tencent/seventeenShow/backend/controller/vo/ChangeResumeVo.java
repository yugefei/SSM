package com.tencent.seventeenShow.backend.controller.vo;

import java.util.List;

/**
 * Created by gefeiyu on 2017/7/15.
 */
public class ChangeResumeVo {
    private String username;
    private String gender;
    private  String birthday;
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
