package com.betting.bettinggameapp.service.impl;

import com.betting.bettinggameapp.dto.UserDto;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.exception.NoSuchEntityException;
import com.betting.bettinggameapp.repository.UserRepository;
import com.betting.bettinggameapp.service.AccountStateService;
import com.betting.bettinggameapp.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountStateService accountStateService;

    public UserServiceImpl(UserRepository userRepository,
                           AccountStateService accountStateService) {
        this.userRepository = userRepository;
        this.accountStateService = accountStateService;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchEntityException(String.format("Could not find User by id = %s", id)));
    }

    @Override
    public User createUser(UserDto userDto) {

        User user = new User.Builder()
                .withFirstName(userDto.getFirstName())
                .withLastName(userDto.getLastName())
                .withNickname(userDto.getNickname())
                .build();

        user = userRepository.save(user);

        accountStateService.createAccountState(user);

        return user;
    }
}
