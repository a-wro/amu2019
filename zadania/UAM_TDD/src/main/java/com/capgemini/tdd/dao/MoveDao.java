package com.capgemini.tdd.dao;

import com.capgemini.tdd.dao.entities.MoveBE;

import java.util.List;

public interface MoveDao
{
    MoveBE save(MoveBE move);
    List<MoveBE> findByBoardId(Long boardId);
    List<MoveBE> findAll();
}
