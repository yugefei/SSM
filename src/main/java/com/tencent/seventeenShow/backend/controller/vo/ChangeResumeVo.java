package com.tencent.seventeenShow.backend.controller.vo;

import java.util.List;

/**
 * Created by gefeiyu on 2017/7/15.
 */
public class ChangeResumeVo {
    private String username;
    private List<String> label;
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
