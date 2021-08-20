package com.betting.bettinggameapp.repository;

import com.betting.bettinggameapp.entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {

    List<GameResult> findAllByUserId(Long userId);
}
