package com.betting.bettinggameapp.service;

import com.betting.bettinggameapp.dto.BetDto;
import com.betting.bettinggameapp.dto.GameResultDto;
import com.betting.bettinggameapp.entity.Bet;

import java.util.List;

public interface BetService {

    Bet createBet(Bet bet);

    List<BetDto> findAllBetsByUserId(long userId);

    GameResultDto placeBet(BetDto betDto);

}
