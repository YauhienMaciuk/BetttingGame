package com.betting.bettinggameapp.casino;

import java.math.BigDecimal;

public class GameContext {
    private BigDecimal balance;
    private BigDecimal betAmount;
    private boolean freeBet;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public boolean isFreeBet() {
        return freeBet;
    }

    public void setFreeBet(boolean freeBet) {
        this.freeBet = freeBet;
    }

    @Override
    public String toString() {
        return "GameContext{" +
                "balance=" + balance +
                ", betAmount=" + betAmount +
                ", freeBet=" + freeBet +
                '}';
    }
}
