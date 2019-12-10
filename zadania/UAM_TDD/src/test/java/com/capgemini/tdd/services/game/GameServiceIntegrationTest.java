package com.capgemini.tdd.services.game;

import com.capgemini.tdd.core.data.BoardStatusAndMovePair;
import com.capgemini.tdd.core.data.BoardStatusEnum;
import com.capgemini.tdd.core.exceptions.IllegalMoveException;
import com.capgemini.tdd.core.exceptions.NewGameException;
import com.capgemini.tdd.dao.entities.BoardBE;
import com.capgemini.tdd.dao.entities.MatchResultBE;
import com.capgemini.tdd.dao.entities.MoveBE;
import com.capgemini.tdd.services.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceIntegrationTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchResultService matchResultService;

    @Autowired
    private MoveService moveService;

    @Test
    public void shouldMakeMovesToFinishGame() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);

        //when
        long boardId = boardBE.getId();
        gameService.makeMove(boardId, 0L, 0L, "Adam", "X");
        gameService.makeMove(boardId, 2L, 0L, "Kasztan", "O");

        gameService.makeMove(boardId, 0L, 1L, "Adam", "X");
        BoardStatusEnum tempBoardStatusEnum = gameService
                .makeMove(boardId, 2L, 1L, "Kasztan", "O")
                .getBoardStatus();

        BoardStatusEnum finalBoardStatusEnum = gameService
                .makeMove(boardId, 0L, 2L, "Adam", "X")
                .getBoardStatus();

        //then
        Assert.assertEquals(BoardStatusEnum.IN_PROGRESS, tempBoardStatusEnum);
        Assert.assertEquals(BoardStatusEnum.GAME_OVER, finalBoardStatusEnum);
        MatchResultBE matchResult = matchResultService.findByBoardId(boardId);
        Assert.assertNotNull(matchResult);
        Assert.assertEquals("Adam", matchResult.getWinner().getName());
    }

    // 2x pod rzad ten sam gracz
    @Test(expected = IllegalMoveException.class)
    public void shouldThrowWhenSameUserMovesTwiceInRow() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);
        long boardId = boardBE.getId();

        //when
        gameService.makeMove(boardId, 0L, 0L, "Adam", "X");
        gameService.makeMove(boardId, 2L, 0L, "Adam", "X");


        //then
        Assert.fail();
    }

    // gracz stawia nie swoj obiekt
    @Test(expected = IllegalMoveException.class)
    public void shouldThrowWhenPlayerChoosesWrongValue() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);
        long boardId = boardBE.getId();

        //when
        gameService.makeMove(boardId, 0L, 0L, "Adam", "X");
        gameService.makeMove(boardId, 2L, 0L, "Kasztan", "O");
        gameService.makeMove(boardId, 2L, 0L, "Adam", "O");

        //then
        Assert.fail();
    }

    // ruch na juz zajete pole
    @Test(expected = IllegalMoveException.class)
    public void shouldThrowWhenPlayerMovesToTakenPosition() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);

        //when
        long boardId = boardBE.getId();
        gameService.makeMove(boardId, 0L, 0L, "Adam", "X");
        gameService.makeMove(boardId, 0L, 0L, "Kasztan", "O");

        //then
        Assert.fail();
    }

    // gra miedzy graczem A oraz A (nie mozna grac ze soba)
    @Test(expected = NewGameException.class)
    public void shouldThrowForDuplicatePlayerName() {

        //given
        String playerName = "klon";

        //when
        gameService.startNewGame(playerName, playerName);

        //then
        Assert.fail();
    }

    // ruch poza plansze
    @Test(expected = IllegalMoveException.class)
    public void shouldThrowWhenOutOfBoardBounds() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);
        long boardId = boardBE.getId();

        //when
        gameService.makeMove(boardId, 10L, 20L, "Adam", "X");

        //then
        Assert.fail();
    }

    // sprawdzic stan planszy gdy gra skonczona i znowu gracz probuje zrobic ruch
    @Test
    public void shouldReturnGameOverStatus() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);
        long boardId = boardBE.getId();

        //when
        gameService.makeMove(boardId, 0L, 0L, "Adam", "X");
        gameService.makeMove(boardId, 2L, 0L, "Kasztan", "O");

        gameService.makeMove(boardId, 0L, 1L, "Adam", "X");
        BoardStatusEnum tempBoardStatusEnum = gameService
                .makeMove(boardId, 2L, 1L, "Kasztan", "O")
                .getBoardStatus();

        BoardStatusEnum finalBoardStatusEnum = gameService
                .makeMove(boardId, 0L, 2L, "Adam", "X")
                .getBoardStatus();

        BoardStatusAndMovePair afterGameOver = gameService
                .makeMove(boardId, 2L, 0L, "Kasztan", "O");

        //then
        Assert.assertEquals(BoardStatusEnum.IN_PROGRESS, tempBoardStatusEnum);
        Assert.assertEquals(BoardStatusEnum.GAME_OVER, finalBoardStatusEnum);
        Assert.assertEquals(BoardStatusEnum.GAME_OVER, afterGameOver.getBoardStatus());
        MatchResultBE matchResult = matchResultService.findByBoardId(boardId);

        Assert.assertNotNull(matchResult);
        Assert.assertEquals("Adam", matchResult.getWinner().getName());

        List<MoveBE> moves = moveService.findByBoardId(boardId);


        MoveBE lastMove = moves.get(moves.size() - 1);

        // gameService should just return the latest move id and not a new one
        Assert.assertEquals(lastMove.getId(), afterGameOver.getMoveId());

    }

    // inne niewymienione przypadki - niepoprawna wartosc ruchu
    @Test(expected = IllegalMoveException.class)
    public void shouldThrowForIncorrectMoveValue() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);
        long boardId = boardBE.getId();

        //when
        gameService.makeMove(boardId, 0L, 0L, "Adam", "DEFINETELY NOT X OR O");

        //then
        Assert.fail();
    }

    // inne niewymienione przypadki - gracz A i B wybieraja ten sam ksztalt
    @Test(expected = IllegalMoveException.class)
    public void shouldThrowForDuplicateMoveValue() {
        //given
        BoardBE boardBE = new BoardBE(userService.findByName("Adam"), userService.findByName("Kasztan"));
        boardBE = boardService.save(boardBE);
        long boardId = boardBE.getId();

        //when
        gameService.makeMove(boardId, 0L, 0L, "Adam", "X");
        gameService.makeMove(boardId, 1L, 1L, "Kasztan", "X");

        //then
        Assert.fail();
    }
}
