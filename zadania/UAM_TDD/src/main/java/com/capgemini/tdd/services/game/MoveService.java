package com.capgemini.tdd.services.game;

import com.capgemini.tdd.dao.entities.MoveBE;

import java.util.List;

public interface MoveService
{

    MoveBE makeMove(Long boardId, Long x, Long y, String playerName, String value);

    List<MoveBE> findByBoardId(Long boardId);

    List<MoveBE> findAll();

    MoveBE lastMoveForBoard(Long boardId);

}
