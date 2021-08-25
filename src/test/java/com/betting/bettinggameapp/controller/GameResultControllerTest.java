package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.casino.Slot;
import com.betting.bettinggameapp.entity.Bet;
import com.betting.bettinggameapp.entity.GameResult;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.repository.GameResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameResultRepository gameResultRepository;

    @Test
    void findAllByUserIdTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Yauheni");
        user.setLastName("Matsiuk");
        user.setNickname("ymatsiuk");

        Bet bet = new Bet();
        bet.setId(2L);
        bet.setUser(user);
        bet.setPlayedSlot(Slot.WIN_TWENTY_EURO);
        bet.setFreeBet(false);
        bet.setCreatedDateTime(LocalDateTime.now());
        bet.setWinAmount(BigDecimal.valueOf(20));
        bet.setBetAmount(BigDecimal.TEN);

        List<GameResult> gameResults = new ArrayList<>();
        GameResult gameResult = new GameResult();
        gameResult.setId(3L);
        gameResult.setWinAmount(BigDecimal.valueOf(20));
        gameResult.setUser(user);
        gameResult.setBets(Arrays.asList(bet));

        gameResults.add(gameResult);

        Mockito.when(gameResultRepository.findAllByUserId(user.getId())).thenReturn(gameResults);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/" + user.getId() + "/game-result")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(gameResult.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].winAmount").value(gameResult.getWinAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bets").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bets[0].id").value(bet.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bets[0].userId").value(bet.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bets[0].betAmount").value(bet.getBetAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bets[0].winAmount").value(bet.getWinAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bets[0].playedSlot").value(bet.getPlayedSlot().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bets[0].freeBet").value(bet.isFreeBet()));
    }
}
