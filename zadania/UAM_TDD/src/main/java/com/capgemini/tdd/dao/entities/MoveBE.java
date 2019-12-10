package com.capgemini.tdd.dao.entities;

import com.capgemini.tdd.dao.enums.MoveValueEnum;

public class MoveBE
{

    private Long id;
    private Long X;
    private Long Y;
    private UserBE player;
    private BoardBE board;
    private MoveValueEnum moveValue;

    public MoveBE(final Long pX, final Long pY, final UserBE pPlayer, final BoardBE pBoard, final MoveValueEnum pMoveValue)
    {
        X = pX;
        Y = pY;
        player = pPlayer;
        board = pBoard;
        moveValue = pMoveValue;
    }

    public void setId(final Long pId)
    {
        id = pId;
    }

    public Long getId()
    {
        return id;
    }

    public Long getX()
    {
        return X;
    }

    public Long getY()
    {
        return Y;
    }

    public UserBE getPlayer()
    {
        return player;
    }

    public BoardBE getBoard()
    {
        return board;
    }

    public MoveValueEnum getMoveValue()
    {
        return moveValue;
    }

    @Override
    public String toString()
    {
        return "MoveBE{" +
               "id=" + id +
               ", X=" + X +
               ", Y=" + Y +
               ", player=" + player +
               ", board=" + board +
               ", moveValue=" + moveValue +
               '}';
    }
}
