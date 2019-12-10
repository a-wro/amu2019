package com.capgemini.tdd.services.requestparams;

public class NewGameRequestParams
{

    private String playerOneName;
    private String playerTwoName;

    public NewGameRequestParams(final String pPlayerOneName, final String pPlayerTwoName)
    {
        playerOneName = pPlayerOneName;
        playerTwoName = pPlayerTwoName;
    }

    public String getPlayerOneName()
    {
        return playerOneName;
    }

    public void setPlayerOneName(final String pPlayerOneName)
    {
        playerOneName = pPlayerOneName;
    }

    public String getPlayerTwoName()
    {
        return playerTwoName;
    }

    public void setPlayerTwoName(final String pPlayerTwoName)
    {
        playerTwoName = pPlayerTwoName;
    }

    @Override
    public String toString()
    {
        return "NewGameRequestParams{" +
               "playerOneName='" + playerOneName + '\'' +
               ", playerTwoName='" + playerTwoName + '\'' +
               '}';
    }
}
