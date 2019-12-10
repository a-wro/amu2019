package com.capgemini.tdd.services.game.impl;

import com.capgemini.tdd.dao.MoveDao;
import com.capgemini.tdd.dao.entities.BoardBE;
import com.capgemini.tdd.dao.entities.MoveBE;
import com.capgemini.tdd.dao.entities.UserBE;
import com.capgemini.tdd.dao.enums.MoveValueEnum;
import com.capgemini.tdd.services.game.BoardService;
import com.capgemini.tdd.services.game.MoveService;
import com.capgemini.tdd.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveServiceImpl implements MoveService {

    @Autowired
    private MoveDao moveDao;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;


    @Override
    public MoveBE lastMoveForBoard(Long boardId) {
        List<MoveBE> movesForBoard = findByBoardId(boardId);

        int size = movesForBoard.size();

        if (size > 0) {
            return movesForBoard.get(size - 1);
        }

        return null;
    }

    @Override
    public MoveBE makeMove(final Long boardId, final Long x, final Long y, final String playerName, final String value) {


        BoardBE boardBE = boardService.findById(boardId);

        UserBE userBE = userService.findByName(playerName);
        MoveValueEnum moveValue = MoveValueEnum.fromCode(value);

        MoveBE moveBE = new MoveBE(x, y, userBE, boardBE, moveValue);
        return moveDao.save(moveBE);
    }

    @Override
    public List<MoveBE> findByBoardId(final Long boardId) {
        return moveDao.findByBoardId(boardId);
    }

    @Override
    public List<MoveBE> findAll() {
        return moveDao.findAll();
    }
}
