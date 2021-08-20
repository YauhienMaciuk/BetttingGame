package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.dto.UserDto;
import com.betting.bettinggameapp.entity.AccountState;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.repository.AccountStateRepository;
import com.betting.bettinggameapp.repository.UserRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String FIRST_NAME = "Yauheni";
    private static final String LAST_NAME = "Matsiuk";
    private static final String NICKNAME = "ymatsiuk";
    private static final String STRING_WITH_30_CHARACTERS = "123456789012345678901234567890";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountStateRepository accountStateRepository;

    @Test
    void findUserByIdTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setNickname(NICKNAME);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(NICKNAME));
    }

    @Test
    void createUserTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setNickname(NICKNAME);

        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setNickname(NICKNAME);

        Mockito.when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value(NICKNAME));
    }

    @Test
    void tryToCreateUserWithoutNicknameTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("[\"The nickname may not be blank\"]",
                        result.getResponse().getContentAsString()));
    }

    @Test
    void tryToCreateUserWithoutFirstNameTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setLastName(LAST_NAME);
        userDto.setNickname(NICKNAME);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("[\"The firstName may not be blank\"]",
                        result.getResponse().getContentAsString()));
    }

    @Test
    void tryToCreateUserWithoutLastNameTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setNickname(NICKNAME);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("[\"The lastName may not be blank\"]",
                        result.getResponse().getContentAsString()));
    }

    @Test
    void tryToCreateUserWithFirstNameBiggerThan25CharactersTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName(STRING_WITH_30_CHARACTERS);
        userDto.setLastName(LAST_NAME);
        userDto.setNickname(NICKNAME);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("[\"length must be between 1 and 25\"]",
                        result.getResponse().getContentAsString()));
    }

    @Test
    void tryToCreateUserWithLastNameBiggerThan25CharactersTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(STRING_WITH_30_CHARACTERS);
        userDto.setNickname(NICKNAME);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("[\"length must be between 1 and 25\"]",
                        result.getResponse().getContentAsString()));
    }

    @Test
    void tryToCreateUserWithNicknameBiggerThan25CharactersTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setNickname(STRING_WITH_30_CHARACTERS);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("[\"length must be between 1 and 25\"]",
                        result.getResponse().getContentAsString()));
    }
}
