package com.capgemini.tdd.services.game;

import com.capgemini.tdd.dao.entities.BoardBE;
import com.capgemini.tdd.dao.entities.MoveBE;
import com.capgemini.tdd.dao.entities.UserBE;
import com.capgemini.tdd.dao.impl.MoveDaoImpl;
import com.capgemini.tdd.services.game.impl.MoveServiceImpl;
import com.capgemini.tdd.services.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MoveServiceMockTest
{

    @Spy
    private MoveDaoImpl moveDao;

    @Mock
    private BoardService boardService;

    @Mock
    private UserService userService;

    @InjectMocks
    private MoveServiceImpl moveService;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        this.moveDao.setup();
    }

    @Test
    public void shouldMakeMove()
    {
        //given
        int numOfMoveEntries = moveService.findAll().size();
        UserBE user1 = new UserBE("bubu");
        Mockito.when(userService.findByName("bubu")).thenReturn(user1);
        UserBE user2 = new UserBE("bubu1");
        BoardBE boardBE = new BoardBE(user1, user2);
        Mockito.when(boardService.findById(1L)).thenReturn(boardBE);

        //when
        MoveBE moveBE = moveService.makeMove(1L, 1L, 1L, user1.getName(), "X");

        //then
        Assert.assertNotNull(moveBE.getId());
        Assert.assertEquals(boardBE, moveBE.getBoard());
        Assert.assertEquals(numOfMoveEntries+1, moveService.findAll().size());
    }

}
