package com.capgemini.tdd.core.enums;

public enum GameError {

    ILLEGAL_MOVE("Illegal move"),
    OUT_OF_BOUNDS("Out of bounds"),
    DUPLICATE_MOVE_VALUE("Duplicate move value"),
    WRONG_VALUE_FOR_PLAYER("Wrong move value for player"),
    POSITION_TAKEN("Position taken"),
    NOT_PLAYERS_TURN("Not player's turn"),
    DUPLICATE_PLAYER_NAMES("Duplicate player names");


    private String message;

    GameError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
