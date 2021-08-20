package com.betting.bettinggameapp.casino;

import com.betting.bettinggameapp.entity.Bet;

import java.math.BigDecimal;

public class RoundResult {
    private BigDecimal winAmount;
    private boolean givenFreeBet;
    private Bet bet;
    private Slot playedSlot;

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public boolean isGivenFreeBet() {
        return givenFreeBet;
    }

    public void setGivenFreeBet(boolean givenFreeBet) {
        this.givenFreeBet = givenFreeBet;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Slot getPlayedSlot() {
        return playedSlot;
    }

    public void setPlayedSlot(Slot playedSlot) {
        this.playedSlot = playedSlot;
    }

    @Override
    public String toString() {
        return "RoundResult{" +
                "winAmount=" + winAmount +
                ", givenFreeBet=" + givenFreeBet +
                ", bet=" + bet +
                ", playedSlot=" + playedSlot +
                '}';
    }
}
