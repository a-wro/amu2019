package com.capgemini.tdd.services.game;

import com.capgemini.tdd.dao.entities.BoardBE;

import java.util.List;

public interface BoardService
{

    BoardBE findById(Long id);
    List<BoardBE> findAll();
    BoardBE save(BoardBE boardBE);
    BoardBE findByPlayersNames(String playerOne, String playerTwo);

}
