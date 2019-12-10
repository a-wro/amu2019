package com.capgemini.tdd.core.data;

public class BoardStatusAndMovePair {

    private Long moveId;
    private BoardStatusEnum boardStatus;

    public BoardStatusAndMovePair() {
    }

    public BoardStatusAndMovePair(Long moveId, BoardStatusEnum boardStatus) {
        this.moveId = moveId;
        this.boardStatus = boardStatus;
    }

    public Long getMoveId() {
        return moveId;
    }

    public BoardStatusEnum getBoardStatus() {
        return boardStatus;
    }
}
