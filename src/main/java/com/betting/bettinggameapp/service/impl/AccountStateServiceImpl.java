package com.betting.bettinggameapp.service.impl;

import com.betting.bettinggameapp.dto.AccountStateDto;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.entity.AccountState;
import com.betting.bettinggameapp.exception.NoSuchEntityException;
import com.betting.bettinggameapp.repository.AccountStateRepository;
import com.betting.bettinggameapp.service.AccountStateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountStateServiceImpl implements AccountStateService {

    private final static BigDecimal INITIAL_AMOUNT = BigDecimal.valueOf(5000);

    private final AccountStateRepository accountStateRepository;

    public AccountStateServiceImpl(AccountStateRepository accountStateRepository) {
        this.accountStateRepository = accountStateRepository;
    }

    @Override
    public AccountState createAccountState(User user) {
        AccountState accountState = new AccountState();
        accountState.setBalance(INITIAL_AMOUNT);
        accountState.setInitialAmount(INITIAL_AMOUNT);
        accountState.setUser(user);
        return accountStateRepository.save(accountState);
    }

    @Override
    public AccountStateDto findAccountStateDtoByUserId(long userId) {
        AccountState accountState = findByUserId(userId);
        return AccountStateDto.of(accountState);
    }

    @Override
    public AccountState findByUserId(long userId) {
        AccountState accountState = accountStateRepository.findByUserId(userId);
        if (accountState == null) {
            throw new NoSuchEntityException(String.format("Could not find accountState by userId = %s", userId));
        }
        return accountState;
    }

    @Override
    public AccountState updateAccountState(AccountStateDto accountStateDto) {
        AccountState accountState = findById(accountStateDto.getId());
        accountState.setBalance(accountStateDto.getBalance());
        return accountStateRepository.save(accountState);
    }

    @Override
    public AccountState findById(long id) {
        AccountState accountState = accountStateRepository.findByUserId(id);
        if (accountState == null) {
            throw new NoSuchEntityException(String.format("Could not find accountState by id = %s", id));
        }
        return accountState;
    }
}
