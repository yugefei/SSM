package com.tencent.seventeenShow.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Edward on 2017/2/7 007.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User {
    private Long id;
    private String mobile;
    private String birthday;
    private String gender;
    private String nickname;
    private String avatar;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
