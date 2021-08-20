package com.betting.bettinggameapp.service;

import com.betting.bettinggameapp.dto.AccountStateDto;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.entity.AccountState;

public interface AccountStateService {

    AccountState createAccountState(User user);

    AccountStateDto findAccountStateDtoByUserId(long userId);

    AccountState findByUserId(long userId);

    AccountState updateAccountState(AccountStateDto accountStateDto);

    AccountState findById(long id);

}
