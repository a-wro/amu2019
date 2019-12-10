package com.capgemini.tdd.services.TO;

import com.capgemini.tdd.dao.enums.MoveValueEnum;

public class MoveTO
{

    private Long X;
    private Long Y;
    private UserTO player;
    private BoardTO board;
    private String moveValue;

    public MoveTO(final Long pX, final Long pY, final UserTO pPlayer, final BoardTO pBoard, final String pMoveValue)
    {
        X = pX;
        Y = pY;
        player = pPlayer;
        board = pBoard;
        moveValue = pMoveValue;
    }

    public Long getX()
    {
        return X;
    }

    public Long getY()
    {
        return Y;
    }

    public UserTO getPlayer()
    {
        return player;
    }

    public BoardTO getBoard()
    {
        return board;
    }

    public String getMoveValue()
    {
        return moveValue;
    }

    @Override
    public String toString()
    {
        return "MoveTO{" +
               "X=" + X +
               ", Y=" + Y +
               ", player='" + player + '\'' +
               ", board='" + board + '\'' +
               ", moveValue=" + moveValue +
               '}';
    }
}
