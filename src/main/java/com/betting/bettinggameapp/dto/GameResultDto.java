package com.betting.bettinggameapp.dto;

import com.betting.bettinggameapp.entity.GameResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class GameResultDto {
    private long id;
    private BigDecimal winAmount;
    private List<BetDto> bets;

    public static GameResultDto of(GameResult gameResult) {
        GameResultDto gameResultDto = new GameResultDto();
        gameResultDto.setId(gameResult.getId());
        gameResultDto.setWinAmount(gameResult.getWinAmount());

        List<BetDto> betDtos = gameResult.getBets().stream()
                .map(BetDto::of)
                .collect(Collectors.toList());

        gameResultDto.setBets(betDtos);
        return gameResultDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public List<BetDto> getBets() {
        return bets;
    }

    public void setBets(List<BetDto> bets) {
        this.bets = bets;
    }

    @Override
    public String toString() {
        return "GameResultDto{" +
                "id=" + id +
                ", winAmount=" + winAmount +
                ", bets=" + bets +
                '}';
    }
}
