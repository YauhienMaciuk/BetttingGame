package com.betting.bettinggameapp.dto;

import com.betting.bettinggameapp.entity.AccountState;

import java.math.BigDecimal;

public class AccountStateDto {
    private long id;
    private long userId;
    private BigDecimal balance;
    private BigDecimal initialAmount;

    public static AccountStateDto of(AccountState accountState) {
        AccountStateDto accountStateDto = new AccountStateDto();
        accountStateDto.setId(accountState.getId());
        accountStateDto.setUserId(accountState.getUser().getId());
        accountStateDto.setBalance(accountState.getBalance());
        accountStateDto.setInitialAmount(accountState.getInitialAmount());

        return accountStateDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    @Override
    public String toString() {
        return "AccountStateDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", balance=" + balance +
                ", initialAmount=" + initialAmount +
                '}';
    }
}
