package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.dto.LeagueDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.League;

import java.util.stream.Collectors;

public class LeagueDtoEntityConverter {

    private LeagueDtoEntityConverter() {
        //Util class
    }

    public static LeagueDTO convertEntityToDTO(League league) {
        LeagueDTO leagueDTO = new LeagueDTO();
        leagueDTO.setId(league.getId());
        leagueDTO.setName(league.getName());
        leagueDTO.setDescription(league.getDescription());
        leagueDTO.setStartDate(league.getStartDate());
        leagueDTO.setEndDate(league.getEndDate());
        leagueDTO.setRegisterOpen(league.getRegisterOpen());
        leagueDTO.setRegisterBy(league.getRegisterBy());
        leagueDTO.setScoreBasis(league.getScoreBasis());
        leagueDTO.setHandicapPct(league.getHandicapPct());


        leagueDTO.setTeams(league.getTeams().stream().map(TeamDtoEntityConvertor::entityToResponseDTO).collect(
                Collectors.toSet()));
        return leagueDTO;
    }
}
