package com.betting.bettinggameapp.dto;

import com.betting.bettinggameapp.casino.Slot;
import com.betting.bettinggameapp.entity.Bet;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BetDto {
    private long id;
    @NotNull(message = "userId must not be blank")
    private long userId;
    @NotNull(message = "betAmount value must not be null")
    @DecimalMin(value = "1", message = "betAmount must be bigger than or equal 1")
    @DecimalMax(value = "10", message = "betAmount must be less than or equal 10")
    private BigDecimal betAmount;
    private BigDecimal winAmount;
    private LocalDateTime createdDateTime;
    private Slot playedSlot;
    private boolean freeBet;

    public static BetDto of(Bet bet) {
        BetDto betDto = new BetDto();
        betDto.setId(bet.getId());
        betDto.setUserId(bet.getUser().getId());
        betDto.setBetAmount(bet.getBetAmount());
        betDto.setWinAmount(bet.getWinAmount());
        betDto.setFreeBet(bet.isFreeBet());
        betDto.setCreatedDateTime(bet.getCreatedDateTime());
        betDto.setPlayedSlot(bet.getPlayedSlot());
        return betDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "BetDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", betAmount=" + betAmount +
                ", winAmount=" + winAmount +
                ", createdDateTime=" + createdDateTime +
                ", playedSlot=" + playedSlot +
                ", freeBet=" + freeBet +
                '}';
    }
}
