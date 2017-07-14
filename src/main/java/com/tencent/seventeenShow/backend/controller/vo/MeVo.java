package com.tencent.seventeenShow.backend.controller.vo;

import java.util.List;

/**
 * Created by gefeiyu on 2017/7/12.
 */
public class MeVo {
    private String avatar;

    private String userName;

    private Long loveBalances;

    private Long diamondBalances;

    private List<String> label;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getLoveBalances() {
        return loveBalances;
    }

    public void setLoveBalances(Long loveBalances) {
        this.loveBalances = loveBalances;
    }

    public Long getDiamondBalances() {
        return diamondBalances;
    }

    public void setDiamondBalances(Long diamondBalances) {
        this.diamondBalances = diamondBalances;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }
}
