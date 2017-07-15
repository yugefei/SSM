package com.tencent.seventeenShow.backend.model;

import java.util.List;

/**
 * Created by gefeiyu on 2017/7/14.
 */
public class FriendInfo {
    private String username;
    private int age;
    private List<String> label;
    private String photo;
    private int loveBalance;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getLoveBalance() {
        return loveBalance;
    }

    public void setLoveBalance(int loveBalance) {
        this.loveBalance = loveBalance;
    }
}
