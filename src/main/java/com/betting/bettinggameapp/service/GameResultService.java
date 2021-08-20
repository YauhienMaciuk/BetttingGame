package com.betting.bettinggameapp.service;

import com.betting.bettinggameapp.dto.GameResultDto;
import com.betting.bettinggameapp.entity.GameResult;

import java.util.List;

public interface GameResultService {

    GameResult createGameResult(GameResult gameResult);

    List<GameResultDto> findAllGameResultDtosByUserId(Long userId);
}
