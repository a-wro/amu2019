package com.capgemini.tdd.dao.entities;

import com.capgemini.tdd.dao.enums.MatchResultEnum;

public class MatchResultBE
{

    private Long id;
    private BoardBE board;
    private UserBE playerOne;
    private UserBE playerTwo;
    private UserBE winner;

    public MatchResultBE(final BoardBE pBoard, final UserBE pPlayerOne, final UserBE pPlayerTwo, final UserBE pWinner)
    {
        board = pBoard;
        playerOne = pPlayerOne;
        playerTwo = pPlayerTwo;
        winner = pWinner;
    }

    public void setId(final Long pId)
    {
        id = pId;
    }

    public Long getId()
    {
        return id;
    }

    public BoardBE getBoard()
    {
        return board;
    }

    public UserBE getPlayerOne()
    {
        return playerOne;
    }

    public UserBE getPlayerTwo()
    {
        return playerTwo;
    }

    public UserBE getWinner()
    {
        return winner;
    }

    @Override
    public String toString()
    {
        return "MatchResultBE{" +
               "id=" + id +
               ", board=" + board +
               ", playerOne=" + playerOne +
               ", playerTwo=" + playerTwo +
               ", winner=" + winner +
               '}';
    }
}
