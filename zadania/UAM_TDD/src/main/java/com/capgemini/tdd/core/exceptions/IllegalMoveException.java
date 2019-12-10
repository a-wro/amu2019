package com.capgemini.tdd.core.exceptions;

import com.capgemini.tdd.core.enums.GameError;

public class IllegalMoveException extends RuntimeException {

    private GameError gameError;

    public IllegalMoveException(GameError gameError) {
        this.gameError = gameError;
    }

    public GameError getGameError() {
        return gameError;
    }
}
