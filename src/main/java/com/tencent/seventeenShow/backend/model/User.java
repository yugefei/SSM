package com.tencent.seventeenShow.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by Edward on 2017/2/7 007.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User {
    private String mobile;
    private String birthday;
    private String gender;
    private int age;
    private String avatar;
    private String username;
    private List<String> label;
    private String openId;
    private int diamondBalance;
    private int loveBalance;
    private int dislikeLeft;
    private boolean localMatch;//true 表示开启本地匹配 ， false 表示没有开启本地匹配

    public boolean getLocalMatch() {
        return localMatch;
    }

    public void setLocalMatch(boolean localMatch) {
        this.localMatch = localMatch;
    }

    public int getDislikeLeft() {
        return dislikeLeft;
    }

    public void setDislikeLeft(int dislikeLeft) {
        this.dislikeLeft = dislikeLeft;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getDiamondBalance() {
        return diamondBalance;
    }

    public void setDiamondBalance(int diamondBalance) {
        this.diamondBalance = diamondBalance;
    }

    public int getLoveBalance() {
        return loveBalance;
    }

    public void setLoveBalance(int loveBalance) {
        this.loveBalance = loveBalance;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object obj) {
        return ((User)obj).getOpenId().equals(this.getOpenId());
    }
}
