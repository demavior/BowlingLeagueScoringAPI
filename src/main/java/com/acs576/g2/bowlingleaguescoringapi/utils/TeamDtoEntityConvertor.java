package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.dto.response.TeamResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Team;

import java.util.stream.Collectors;

public class TeamDtoEntityConvertor {
    public static TeamResponseDTO entityToResponseDTO(Team team) {
        TeamResponseDTO teamResponseDTO = new TeamResponseDTO();

        teamResponseDTO.setId(team.getId());
        teamResponseDTO.setName(team.getName());

        teamResponseDTO.setPlayers(team.getPlayers().stream().map(player -> {
            TeamResponseDTO.TeamPlayer teamPlayer = new TeamResponseDTO.TeamPlayer();

            teamPlayer.setId(player.getId());
            teamPlayer.setName(player.getUserDetails().getFirstName());
            teamPlayer.setEmail(player.getUserDetails().getUserCredentials().getEmail());
            teamPlayer.setAverage(player.getAverage());

            return teamPlayer;
        }).collect(Collectors.toSet()));

        return teamResponseDTO;
    }
}
