package com.capgemini.tdd.services.game;

import com.capgemini.tdd.dao.entities.MatchResultBE;

import java.util.List;

public interface MatchResultService {
    MatchResultBE save(MatchResultBE matchResult);

    List<MatchResultBE> findAll();

    MatchResultBE findByBoardId(Long boardId);
}
