package com.betting.bettinggameapp.lock;

public interface Unlockable extends AutoCloseable
{
    void unlock();

    @Override
    void close();
}