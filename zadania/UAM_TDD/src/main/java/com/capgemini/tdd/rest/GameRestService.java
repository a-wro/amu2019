package com.capgemini.tdd.rest;

import com.capgemini.tdd.core.data.BoardStatusAndMovePair;
import com.capgemini.tdd.mappers.BoardMapper;
import com.capgemini.tdd.services.TO.BoardTO;
import com.capgemini.tdd.services.game.GameService;
import com.capgemini.tdd.services.requestparams.MoveRequestParams;
import com.capgemini.tdd.services.requestparams.NewGameRequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/game")
public class GameRestService {

    @Autowired
    private GameService gameService;

    @PostMapping(value = "/new")
    @ResponseBody
    public BoardTO startNewGame(@RequestBody NewGameRequestParams requestParams) {
        System.out.println(requestParams.toString());
        return BoardMapper.fromBE(gameService.startNewGame(requestParams.getPlayerOneName(), requestParams.getPlayerTwoName()));
    }

    @PostMapping(value = "/{boardId}/move/")
    @ResponseBody
    public BoardStatusAndMovePair makeMove(@PathVariable("boardId") Long boardId, @RequestBody MoveRequestParams requestParams) {
        return gameService.makeMove(boardId, requestParams.getX(), requestParams.getY(), requestParams.getPlayerName(),
                requestParams.getMoveValue().getCode());
    }
}
