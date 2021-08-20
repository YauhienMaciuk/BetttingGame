package com.betting.bettinggameapp.repository;

import com.betting.bettinggameapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
