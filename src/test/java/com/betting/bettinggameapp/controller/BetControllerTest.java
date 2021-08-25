package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.casino.Slot;
import com.betting.bettinggameapp.dto.BetDto;
import com.betting.bettinggameapp.entity.AccountState;
import com.betting.bettinggameapp.entity.Bet;
import com.betting.bettinggameapp.entity.GameResult;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.repository.AccountStateRepository;
import com.betting.bettinggameapp.repository.BetRepository;
import com.betting.bettinggameapp.repository.GameResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BetRepository betRepository;

    @MockBean
    private AccountStateRepository accountStateRepository;

    @MockBean
    private GameResultRepository gameResultRepository;

    @Test
    void findAllByUserId() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Yauheni");
        user.setLastName("Matsiuk");
        user.setNickname("ymatsiuk");

        Bet bet1 = new Bet();
        bet1.setId(2L);
        bet1.setUser(user);
        bet1.setPlayedSlot(Slot.WIN_TWENTY_EURO);
        bet1.setFreeBet(false);
        bet1.setCreatedDateTime(LocalDateTime.now());
        bet1.setWinAmount(BigDecimal.valueOf(20));
        bet1.setBetAmount(BigDecimal.TEN);

        Bet bet2 = new Bet();
        bet2.setId(3L);
        bet2.setUser(user);
        bet2.setPlayedSlot(Slot.LOSE);
        bet2.setFreeBet(false);
        bet2.setCreatedDateTime(LocalDateTime.now());
        bet2.setWinAmount(BigDecimal.valueOf(-20));
        bet2.setBetAmount(BigDecimal.TEN);

        List<Bet> bets = Arrays.asList(bet1, bet2);

        Mockito.when(betRepository.findAllByUserId(user.getId())).thenReturn(bets);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/" + user.getId() + "/bet")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(bet1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(bet1.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].betAmount").value(bet1.getBetAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].winAmount").value(bet1.getWinAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdDateTime").value(bet1.getCreatedDateTime().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].playedSlot").value(bet1.getPlayedSlot().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].freeBet").value(bet1.isFreeBet()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(bet2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(bet2.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].betAmount").value(bet2.getBetAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].winAmount").value(bet2.getWinAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].playedSlot").value(bet2.getPlayedSlot().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].freeBet").value(bet2.isFreeBet()));
    }

    @Test
    void placeBet() throws Exception {
        //given
        BetDto betDto = new BetDto();
        betDto.setUserId(1);
        betDto.setBetAmount(BigDecimal.TEN);

        User user = new User();
        user.setFirstName("Yauheni");
        user.setLastName("Matsiuk");
        user.setNickname("ymatsiuk");
        user.setId(1L);

        Bet bet = new Bet();
        bet.setId(2L);
        bet.setUser(user);
        bet.setPlayedSlot(Slot.WIN_TWENTY_EURO);
        bet.setFreeBet(false);
        bet.setCreatedDateTime(LocalDateTime.now());
        bet.setWinAmount(BigDecimal.valueOf(20));
        bet.setBetAmount(BigDecimal.TEN);

        AccountState accountState = new AccountState();
        accountState.setInitialAmount(BigDecimal.valueOf(5000));
        accountState.setBalance(BigDecimal.valueOf(5000));
        accountState.setUser(user);
        accountState.setId(2L);

        GameResult gameResult = new GameResult();
        gameResult.setId(3L);
        gameResult.setBets(Arrays.asList(bet));
        gameResult.setWinAmount(bet.getWinAmount());
        gameResult.setUser(user);

        //when
        Mockito.when(accountStateRepository.findByUserId(anyLong())).thenReturn(accountState);
        Mockito.when(betRepository.save(any())).thenReturn(bet);
        Mockito.when(gameResultRepository.save(any())).thenReturn(gameResult);

        //then
        mockMvc.perform(post("/bet")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(betDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(gameResult.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winAmount").value(gameResult.getWinAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bets").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bets[0].id").value(gameResult.getBets().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bets[0].userId").value(gameResult.getBets().get(0).getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bets[0].betAmount").value(gameResult.getBets().get(0).getBetAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bets[0].winAmount").value(gameResult.getBets().get(0).getWinAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bets[0].playedSlot").value(gameResult.getBets().get(0).getPlayedSlot().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bets[0].freeBet").value(gameResult.getBets().get(0).isFreeBet()));
    }
}
