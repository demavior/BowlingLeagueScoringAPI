package com.acs576.g2.bowlingleaguescoringapi;

import com.acs576.g2.bowlingleaguescoringapi.dto.GameDTO;
import com.acs576.g2.bowlingleaguescoringapi.controller.GameController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GameController gameController;

    @Test
    void createGame() throws Exception {

        // Prepare test data - create a game DTO
        GameDTO gameDTO = new GameDTO();
        // Set League Id to 1
        gameDTO.setLeague_id(1L);
        // Set other game details
        gameDTO.setGame_week(1L);
        gameDTO.setGame_date(java.sql.Date.valueOf(LocalDate.of(2024,1,1)));

        // Test GameController createdGame
        GameDTO createdGame = gameController.createGame(gameDTO).getBody();
        // Assert that the returned gameDTO is not null
        assertNotNull(createdGame, "Created game should not be null");
        // Assert that the league ID of the created game matches the expected value
        assertEquals(1L, createdGame.getLeague_id(), "League ID should match expected value");
        // Assert that the ID of the created game is not null and greater than 0
        assertNotNull(createdGame.getId(),"Game ID should not be null");
        assertTrue(createdGame.getId() > 0,"Game ID should be greater than 0");

        //assertNotNull(createdGame, "Created game should not be null");
        String jwtToken = "";
        // Send HTTP POST request to the /games endpoint with the game DTO as JSON
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/createGame")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.league_id").exists());


        // Setting team with non-existent league should return error
        gameDTO.setLeague_id(15L);
        //createdGame = gameController.createGame(gameDTO).getBody();
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/createGame")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(gameDTO)))
                // Verify that the response status code is 500 (Internal Server Error)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
