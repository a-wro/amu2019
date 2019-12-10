package com.capgemini.tdd.core.service.impl;

import com.capgemini.tdd.core.data.Board;
import com.capgemini.tdd.core.data.Point;
import com.capgemini.tdd.core.service.BoardBuilder;
import com.capgemini.tdd.dao.entities.MoveBE;
import com.capgemini.tdd.services.game.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardBuilderImpl implements BoardBuilder
{
    @Autowired
    private MoveService moveService;

    @Override
    public Board buildBoard(final Long id)
    {
        Board board = new Board();

        List<MoveBE> moveBEList = moveService.findByBoardId(id);
        moveBEList.forEach(x -> board.put(new Point(x.getX(), x.getY()), x.getMoveValue()));
        return board;
    }

}
