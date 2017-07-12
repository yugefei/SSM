package com.tencent.seventeenShow.backend.model;

/**
 * Created by gefeiyu on 2017/7/10.
 */
public class Privilege {
    private Long diamondBalances;
    private Long loveBalances;

    public Long getDiamondBalances() {
        return diamondBalances;
    }

    public void setDiamondBalances(Long diamondBalances) {
        this.diamondBalances = diamondBalances;
    }

    public Long getLoveBalances() {
        return loveBalances;
    }

    public void setLoveBalances(Long loveBalances) {
        this.loveBalances = loveBalances;
    }
}
