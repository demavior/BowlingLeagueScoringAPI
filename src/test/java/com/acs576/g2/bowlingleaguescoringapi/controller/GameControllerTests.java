package com.acs576.g2.bowlingleaguescoringapi.controller;

import com.acs576.g2.bowlingleaguescoringapi.jwt.JwtAuthenticationFilter;
import com.acs576.g2.bowlingleaguescoringapi.jwt.JwtTokenUtil;
import com.acs576.g2.bowlingleaguescoringapi.jwt.JwtUserDetailsService;
import com.acs576.g2.bowlingleaguescoringapi.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTests {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testGameController_Success() throws Exception {
        mockMvc.perform(
                       MockMvcRequestBuilders.request(HttpMethod.DELETE, "/game/delete/123"))
                .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
