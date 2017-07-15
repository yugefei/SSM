package com.tencent.seventeenShow.backend.controller.vo;

/**
 * Created by EdwardZhou on 2017/7/15.
 * All Rights Reserved
 */
public class LoveBalanceVo {
    private Long loveBalance;

    public Long getLoveBalance() {
        return loveBalance;
    }

    public void setLoveBalance(Long loveBalance) {
        this.loveBalance = loveBalance;
    }


    public LoveBalanceVo(Long loveBalance) {
        this.loveBalance = loveBalance;
    }
}
