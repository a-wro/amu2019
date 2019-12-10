package com.capgemini.tdd.core.exceptions;

import com.capgemini.tdd.core.enums.GameError;

public class NewGameException extends RuntimeException {

    private GameError gameError;

    public NewGameException(GameError gameError) {
        this.gameError = gameError;
    }

    public GameError getGameError() {
        return gameError;
    }
}
