package com.capgemini.tdd.services.TO;

public class MoveResultTO
{
    private String moveResult;

    public MoveResultTO(final String pMoveResult)
    {
        moveResult = pMoveResult;
    }

    public String getMoveResult()
    {
        return moveResult;
    }

    @Override
    public String toString()
    {
        return "MoveResultTO{" +
               "moveResult='" + moveResult + '\'' +
               '}';
    }
}
