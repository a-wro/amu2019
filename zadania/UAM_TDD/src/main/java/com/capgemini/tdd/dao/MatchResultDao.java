package com.capgemini.tdd.dao;

import com.capgemini.tdd.dao.entities.MatchResultBE;

import java.util.List;

public interface MatchResultDao
{

    MatchResultBE save(MatchResultBE matchResult);
    List<MatchResultBE> findAll();
    MatchResultBE findByBoardId(Long boardId);

}
