package com.betting.bettinggameapp.service.impl;

import com.betting.bettinggameapp.dto.GameResultDto;
import com.betting.bettinggameapp.entity.GameResult;
import com.betting.bettinggameapp.exception.NoSuchEntityException;
import com.betting.bettinggameapp.repository.GameResultRepository;
import com.betting.bettinggameapp.service.GameResultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameResultServiceImpl implements GameResultService {

    private final GameResultRepository gameResultRepository;

    public GameResultServiceImpl(GameResultRepository gameResultRepository) {
        this.gameResultRepository = gameResultRepository;
    }

    @Override
    public GameResult createGameResult(GameResult gameResult) {
        return gameResultRepository.save(gameResult);
    }

    @Override
    public List<GameResultDto> findAllGameResultDtosByUserId(Long userId) {
        List<GameResult> gameResults = gameResultRepository.findAllByUserId(userId);

        if (gameResults.isEmpty()) {
            throw new NoSuchEntityException(String.format("Could not find GameResults by userId = %s", userId));
        }

        return gameResults.stream()
                .map(GameResultDto::of)
                .collect(Collectors.toList());
    }
}
