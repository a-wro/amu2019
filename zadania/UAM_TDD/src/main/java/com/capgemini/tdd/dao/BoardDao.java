package com.capgemini.tdd.dao;

import com.capgemini.tdd.dao.entities.BoardBE;

import java.util.List;

public interface BoardDao
{

    List<BoardBE> findAll();

    BoardBE save(BoardBE boardBE);

    BoardBE findById(Long id);

    BoardBE getByPlayersNames(String playerOne, String playerTwo);

}
