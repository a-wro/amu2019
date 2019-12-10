package com.capgemini.tdd.dao.impl;

import com.capgemini.tdd.dao.BoardDao;
import com.capgemini.tdd.dao.entities.BoardBE;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class BoardDaoImpl implements BoardDao
{

    private List<BoardBE> boardList;

    @PostConstruct
    private void setup()
    {
        boardList = new ArrayList<>();
    }

    @Override
    public List<BoardBE> findAll()
    {
        return boardList;
    }

    @Override
    public BoardBE save(final BoardBE boardBE)
    {
        Long id = boardList.stream().map(x -> x.getId()).max(Long::compare).orElse(null);
        if (id == null)
            id = 0L;
        else
            id = id + 1L;
        boardBE.setId(id);
        boardList.add(boardBE);
        return boardBE;
    }

    @Override
    public BoardBE findById(final Long id)
    {
        return boardList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public BoardBE getByPlayersNames(final String playerOne, final String playerTwo)
    {
        return findAll().stream().filter(x -> x.getPlayerOne().equals(playerOne) && x.getPlayerTwo().equals(playerTwo)).max(
                Comparator.comparing(BoardBE::getId)).orElse(null);
    }
}
