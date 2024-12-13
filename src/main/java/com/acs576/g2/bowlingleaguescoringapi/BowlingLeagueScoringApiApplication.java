package com.acs576.g2.bowlingleaguescoringapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BowlingLeagueScoringApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BowlingLeagueScoringApiApplication.class, args);
    }

}
