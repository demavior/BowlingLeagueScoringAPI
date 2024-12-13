package com.acs576.g2.bowlingleaguescoringapi.service;

import com.acs576.g2.bowlingleaguescoringapi.repository.GameRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GameServiceTest {

    @MockBean
    private GameRepository gameRepository;
}
