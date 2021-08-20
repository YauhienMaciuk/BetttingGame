package com.betting.bettinggameapp.repository;

import com.betting.bettinggameapp.entity.AccountState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStateRepository extends JpaRepository<AccountState, Long> {

    AccountState findByUserId(long userId);
}
