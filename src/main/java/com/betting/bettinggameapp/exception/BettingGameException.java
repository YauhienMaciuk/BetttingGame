package com.betting.bettinggameapp.exception;

public class BettingGameException extends RuntimeException {

    public BettingGameException() {
    }

    public BettingGameException(String message) {
        super(message);
    }

    public BettingGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
