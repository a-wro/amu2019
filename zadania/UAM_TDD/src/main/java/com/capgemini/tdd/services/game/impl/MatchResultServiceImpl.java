package com.capgemini.tdd.services.game.impl;

import com.capgemini.tdd.dao.MatchResultDao;
import com.capgemini.tdd.dao.entities.MatchResultBE;
import com.capgemini.tdd.services.game.MatchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchResultServiceImpl implements MatchResultService
{

    @Autowired
    private MatchResultDao matchResultDao;

    @Override
    public MatchResultBE save(final MatchResultBE matchResult)
    {
        Long id = findAll().stream().map(x -> x.getId()).max(Long :: compare).orElse(null);
        if(id == null) id = 0L;
        else id = id+1;
        matchResult.setId(id);
        return matchResultDao.save(matchResult);
    }

    @Override
    public List<MatchResultBE> findAll()
    {
        return matchResultDao.findAll();
    }

    @Override
    public MatchResultBE findByBoardId(final Long boardId)
    {
        return matchResultDao.findByBoardId(boardId);
    }
}
