package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.dto.RoleDto;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsRequestDTO;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserDetailsResponseDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.Role;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserDetails;

import java.util.stream.Collectors;

/**
 * The type UserDetails dto entity converter.
 * Author @OwaizMohammed
 */
public class UserDetailsDtoEntityConverter {

    private UserDetailsDtoEntityConverter() {
        //Util class with static methods only, need not be instanced.
    }

    /**
     * UserDeatils to userDetails dto.
     *
     * @param userDetails the userDeatils
     * @return the userDetails dto
     */
    public static UserDetailsResponseDTO userDetailsToUserDetailsResponseDto(UserDetails userDetails) {
        UserDetailsResponseDTO userDetailsDto = new UserDetailsResponseDTO();

        userDetailsDto.setId(userDetails.getId());
        userDetailsDto.setFirstName(userDetails.getFirstName());
        userDetailsDto.setLastName(userDetails.getLastName());
        userDetailsDto.setMiddleName(userDetails.getMiddleName());
        userDetailsDto.setAge(userDetailsDto.getAge());

        userDetailsDto.setRole(
                userDetails.getUserCredentials().getRoles().stream().map(role -> removeRolePrefix(role.getName()))
                           .collect(Collectors.toSet()));
        userDetailsDto.setEmail(userDetails.getUserCredentials().getEmail());

        return userDetailsDto;
    }

    private static String removeRolePrefix(String roleName) {
        if (roleName.startsWith("ROLE_")) {
            return roleName.substring(5);
        }
        return roleName;
    }


}
