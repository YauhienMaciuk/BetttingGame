package com.betting.bettinggameapp.service;

import com.betting.bettinggameapp.dto.UserDto;
import com.betting.bettinggameapp.entity.User;

public interface UserService {

    User findById(String id);

    User createUser(UserDto userDto);
}
