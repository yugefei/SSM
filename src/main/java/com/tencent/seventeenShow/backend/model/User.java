package com.tencent.seventeenShow.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by Edward on 2017/2/7 007.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Comparable{
    private String mobile;
    private String birthday;
    private String gender;
    private Long age;
    private String avatar;
    private String username;
    private List<String> label;
    private String openId;
    private Long diamondBalance;
    private Long loveBalance;
    private Long dislikeLeft;
    private Boolean localMatch;//true 表示开启本地匹配 ， false 表示没有开启本地匹配
    private String sig;

    public Boolean getLocalMatch() {
        return localMatch;
    }

    public void setLocalMatch(boolean localMatch) {
        this.localMatch = localMatch;
    }

    public Long getDislikeLeft() {
        return dislikeLeft;
    }

    public void setDislikeLeft(Long dislikeLeft) {
        this.dislikeLeft = dislikeLeft;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getDiamondBalance() {
        return diamondBalance;
    }

    public void setDiamondBalance(Long diamondBalance) {
        this.diamondBalance = diamondBalance;
    }

    public Long getLoveBalance() {
        return loveBalance;
    }

    public void setLoveBalance(Long loveBalance) {
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == User.class && ((User) obj).getOpenId().equals(this.getOpenId());

    }

    @Override
    public int compareTo(Object o) {
        return ((User)o).getOpenId().compareTo(this.getOpenId());
    }
}
