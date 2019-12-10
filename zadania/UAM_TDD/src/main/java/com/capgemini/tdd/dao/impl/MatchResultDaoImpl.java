package com.capgemini.tdd.dao.impl;

import com.capgemini.tdd.dao.MatchResultDao;
import com.capgemini.tdd.dao.entities.MatchResultBE;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MatchResultDaoImpl implements MatchResultDao
{

    private List<MatchResultBE> matchResults;

    @PostConstruct
    private void setup()
    {
        matchResults = new ArrayList<>();
    }

    @Override
    public MatchResultBE save(final MatchResultBE matchResult)
    {
        Long id = matchResults.stream().map(x -> x.getId()).max(Long::compare).orElse(null);
        if (id == null)
            id = 0L;
        else
            id = id + 1L;
        matchResult.setId(id);
        matchResults.add(matchResult);
        return matchResult;
    }

    @Override
    public List<MatchResultBE> findAll()
    {
        return matchResults;
    }

    @Override
    public MatchResultBE findByBoardId(final Long boardId)
    {
        return matchResults.stream().filter(x -> x.getBoard().getId().equals(boardId)).findFirst().orElse(null);
    }
}
