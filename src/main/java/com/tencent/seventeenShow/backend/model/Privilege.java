package com.tencent.seventeenShow.backend.model;

/**
 * Created by gefeiyu on 2017/7/10.
 */
public class Privilege {
<<<<<<< HEAD
    int diamondBalances;
    int loveBalances;


    public int getDiamondBalances() {
        return diamondBalances;
    }

    public void setDiamondBalances(int diamondBalances) {
        this.diamondBalances = diamondBalances;
    }

    public int getLoveBalances() {
        return loveBalances;
    }

    public void setLoveBalances(int loveBalances) {
        this.loveBalances = loveBalances;
=======
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
>>>>>>> 75bfe64e995a5ead1697bd7910d49b8d17b62cbd
    }
}
