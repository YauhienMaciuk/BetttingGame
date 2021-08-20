package com.betting.bettinggameapp.entity;

import com.betting.bettinggameapp.casino.Slot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private BigDecimal betAmount;
    private BigDecimal winAmount;
    private LocalDateTime createdDateTime;
    private Slot playedSlot;
    private boolean freeBet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Slot getPlayedSlot() {
        return playedSlot;
    }

    public void setPlayedSlot(Slot playedSlot) {
        this.playedSlot = playedSlot;
    }

    public boolean isFreeBet() {
        return freeBet;
    }

    public void setFreeBet(boolean freeBet) {
        this.freeBet = freeBet;
    }
}
