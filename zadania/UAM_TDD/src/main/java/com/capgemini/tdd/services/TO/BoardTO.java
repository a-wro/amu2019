package com.capgemini.tdd.services.TO;

public class BoardTO
{
    private Long id;
    private UserTO playerOne;
    private UserTO playerTwo;

    public BoardTO(final Long pId, final UserTO pPlayerOne, final UserTO pPlayerTwo)
    {
        id = pId;
        playerOne = pPlayerOne;
        playerTwo = pPlayerTwo;
    }

    public Long getId()
    {
        return id;
    }

    public UserTO getPlayerOne()
    {
        return playerOne;
    }

    public UserTO getPlayerTwo()
    {
        return playerTwo;
    }

    @Override
    public String toString()
    {
        return "BoardTO{" +
               "id=" + id +
               ", playerOne=" + playerOne +
               ", playerTwo=" + playerTwo +
               '}';
    }
}
