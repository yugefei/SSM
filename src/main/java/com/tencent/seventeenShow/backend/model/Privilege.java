package com.tencent.seventeenShow.backend.model;

/**
 * Created by gefeiyu on 2017/7/10.
 */
public class Privilege {
    private Long diamondBalance;
    private Long loveBalance;

    public Privilege(Long diamondBalance, Long loveBalance) {
        this.diamondBalance = diamondBalance;
        this.loveBalance = loveBalance;
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
}
