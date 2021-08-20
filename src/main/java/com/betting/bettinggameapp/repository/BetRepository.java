package com.betting.bettinggameapp.repository;

import com.betting.bettinggameapp.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findAllByUserId(long userId);
}
