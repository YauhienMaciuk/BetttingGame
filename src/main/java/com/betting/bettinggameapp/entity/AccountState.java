package com.betting.bettinggameapp.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class AccountState {
    @Id
    private long id;
    private BigDecimal balance;
    private BigDecimal initialAmount;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}