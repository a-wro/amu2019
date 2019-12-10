package com.capgemini.tdd.services.requestparams;

import com.capgemini.tdd.dao.enums.MoveValueEnum;

public class MoveRequestParams {

    private String playerName;
    private Long x;
    private Long y;
    private MoveValueEnum moveValue;

    public MoveRequestParams() {
    }

    public MoveRequestParams(String playerName, Long x, Long y, MoveValueEnum moveValue) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.moveValue = moveValue;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public MoveValueEnum getMoveValue() {
        return moveValue;
    }
}
