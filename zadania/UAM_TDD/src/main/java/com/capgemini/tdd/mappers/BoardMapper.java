package com.capgemini.tdd.mappers;

import com.capgemini.tdd.dao.entities.BoardBE;
import com.capgemini.tdd.services.TO.BoardTO;

public class BoardMapper
{

    public static BoardTO fromBE(BoardBE boardBE)
    {
        return new BoardTO(boardBE.getId(), UserMapper.fromBE(boardBE.getPlayerOne()), UserMapper.fromBE(boardBE.getPlayerTwo()));
    }

}
