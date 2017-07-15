package com.tencent.seventeenShow.backend.model;

import java.util.ArrayList;

/**
 * Created by gefeiyu on 2017/7/10.
 */
public class User1
{
    private int id;
    private String mobile;
    private String birthday;
    private String gender;
    private String nickname;
    private String avatar;
    private ArrayList<String> label;
    private String name;
    private  int dislikeBalances;

    public int getDislikeBalances() {
        return dislikeBalances;
    }

    public void setDislikeBalances(int dislikeBalances) {
        this.dislikeBalances = dislikeBalances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {

        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
