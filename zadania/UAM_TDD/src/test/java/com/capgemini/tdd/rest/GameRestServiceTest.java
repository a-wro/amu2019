package com.capgemini.tdd.rest;

import com.capgemini.tdd.dao.entities.BoardBE;
import com.capgemini.tdd.dao.entities.UserBE;
import com.capgemini.tdd.dao.enums.MoveValueEnum;
import com.capgemini.tdd.services.TO.BoardTO;
import com.capgemini.tdd.services.game.BoardService;
import com.capgemini.tdd.services.requestparams.MoveRequestParams;
import com.capgemini.tdd.services.requestparams.NewGameRequestParams;
import com.capgemini.tdd.services.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameRestServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateNewGame() throws Exception {
        //given
        NewGameRequestParams newGameRequestParams = new NewGameRequestParams("Adam",
                "Kasztan");
        String jsonStr = serialize(newGameRequestParams);
        //when then
        mockMvc.perform(post("/game/new").content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }

    @Test
    public void shouldMakeMove() throws Exception {
        //given
        MoveRequestParams moveRequestParams = new MoveRequestParams("Adam", 1L, 2L, MoveValueEnum.O);
        String jsonStr = serialize(moveRequestParams);



        String url = "/game/" + prepareBoardForGame() + "/move/";

        //when then
        mockMvc.perform(post(url).content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.moveId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardStatus").exists());
    }

    private Long prepareBoardForGame() {
        UserBE player1 = userService.findByName("Adam");
        UserBE player2 = userService.findByName("Kasztan");

        BoardBE newBoard = new BoardBE(player1, player2);
        newBoard = boardService.save(newBoard);

        return newBoard.getId();
    }

    private String serialize(Object requestData) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {

            jsonStr = mapper.writeValueAsString(requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
