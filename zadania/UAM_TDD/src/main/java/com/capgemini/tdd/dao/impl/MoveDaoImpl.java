package com.capgemini.tdd.dao.impl;

import com.capgemini.tdd.dao.MoveDao;
import com.capgemini.tdd.dao.entities.MoveBE;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MoveDaoImpl implements MoveDao
{

    private List<MoveBE> moveList;

    @PostConstruct
    public void setup()
    {
        moveList = new ArrayList<>();
    }

    @Override
    public MoveBE save(final MoveBE move)
    {
        Long id = moveList.stream().map(x -> x.getId()).max(Long::compare).orElse(null);
        if (id == null)
            id = 0L;
        else
            id = id + 1L;
        move.setId(id);
        moveList.add(move);
        return move;
    }

    @Override
    public List<MoveBE> findByBoardId(final Long boardId)
    {
        return moveList.stream().filter(x -> x.getBoard().getId().equals(boardId)).collect(Collectors.toList());
    }

    @Override
    public List<MoveBE> findAll()
    {
        return moveList;
    }
}
