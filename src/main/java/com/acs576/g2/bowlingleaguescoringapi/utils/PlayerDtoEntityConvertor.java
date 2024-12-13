package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.dto.response.PlayerResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Player;

import java.util.stream.Collectors;

public class PlayerDtoEntityConvertor {

    private PlayerDtoEntityConvertor() {
        //Only static util methods, no need to create instance
    }

    public static PlayerResponseDTO mapEntityToDto(Player player) {
        PlayerResponseDTO playerDTO = new PlayerResponseDTO();
        playerDTO.setPlayerId(player.getId());
        playerDTO.setName(player.getUserDetails().getFirstName());
        playerDTO.setAverage(player.getAverage());
        playerDTO.setUserId(player.getUserDetails().getId());
        playerDTO.setTeams(player.getTeams().stream()
                                 .map(team -> {
                                     PlayerResponseDTO.PlayerTeams playerTeam = new PlayerResponseDTO.PlayerTeams();
                                     playerTeam.setTeamId(team.getId());
                                     playerTeam.setTeamName(team.getName());

                                     return playerTeam;
                                 })
                                 .collect(Collectors.toSet()));
        return playerDTO;
    }

}
