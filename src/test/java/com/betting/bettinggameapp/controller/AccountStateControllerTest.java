package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.entity.AccountState;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.repository.AccountStateRepository;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountStateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountStateRepository accountStateRepository;

    @Test
    void findAccountState() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("Yauheni");
        user.setLastName("Matsiuk");
        user.setNickname("ymatsiuk");

        AccountState accountState = new AccountState();
        accountState.setId(2);
        accountState.setInitialAmount(BigDecimal.valueOf(5000));
        accountState.setBalance(BigDecimal.valueOf(5000));
        accountState.setUser(user);

        Mockito.when(accountStateRepository.findByUserId(user.getId())).thenReturn(accountState);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/" + user.getId() + "/account-state")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(accountState.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(accountState.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(accountState.getBalance()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.initialAmount").value(accountState.getInitialAmount()));
    }
}
