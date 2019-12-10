package com.capgemini.tdd.services.TO;

import com.capgemini.tdd.dao.enums.MatchResultEnum;

public class MatchResultTO
{

    private BoardTO board;
    private UserTO playerOne;
    private UserTO playerTwo;
    private MatchResultEnum result;

    public MatchResultTO(final BoardTO pBoard, final UserTO pPlayerOne, final UserTO pPlayerTwo, final MatchResultEnum pResult)
    {
        board = pBoard;
        playerOne = pPlayerOne;
        playerTwo = pPlayerTwo;
        result = pResult;
    }

    public BoardTO getBoard()
    {
        return board;
    }

    public UserTO getPlayerOne()
    {
        return playerOne;
    }

    public UserTO getPlayerTwo()
    {
        return playerTwo;
    }

    public MatchResultEnum getResult()
    {
        return result;
    }

    @Override
    public String toString()
    {
        return "MatchResultTO{" +
               "board=" + board +
               ", playerOne=" + playerOne +
               ", playerTwo=" + playerTwo +
               ", result=" + result +
               '}';
    }
}
