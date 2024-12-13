package com.acs576.g2.bowlingleaguescoringapi.utils;

import com.acs576.g2.bowlingleaguescoringapi.dto.RoleDto;
import com.acs576.g2.bowlingleaguescoringapi.dto.UserCredentialDTO;
import com.acs576.g2.bowlingleaguescoringapi.entity.UserCredentials;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type User dto entity converter.
 * Author @OwaizMohammed
 */
public class UserCredsDtoEntityConverter {

    private UserCredsDtoEntityConverter() {
        //Util class with static methods only, need not be instanced.
    }

    /**
     * UserCreds entity to userCreds dto.
     *
     * @param userCredentials the user credentials
     * @return the userCreds dto
     */
    public static UserCredentialDTO userCredsToUserCredsDto(UserCredentials userCredentials) {
        UserCredentialDTO userCredentialDto = new UserCredentialDTO();
        userCredentialDto.setId(userCredentials.getId());
        // Password is intentionally not set to avoid security risks
        userCredentialDto.setEmail(userCredentials.getEmail());

        // Convert each Role to RoleDto and collect to Set
        Set<RoleDto> roles = userCredentials.getRoles().stream().map(role -> new RoleDto(removeRolePrefix(role.getName())))
                .collect(Collectors.toSet());
        userCredentialDto.setRoles(roles);
        return userCredentialDto;
    }

    private static String removeRolePrefix(String roleName) {
        if (roleName.startsWith("ROLE_")) {
            return roleName.substring(5);
        }
        return roleName;
    }

    /**
     * UserCreds dto to userCreds entity.
     *
     * @param userCredentialDto the user credentials dto
     * @return the UserCreds
     */
    public static UserCredentials userCredsDtoToUser(UserCredentialDTO userCredentialDto) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(userCredentialDto.getEmail());

        // Roles and password will be set later are assumed to be set later as per the instruction
        return userCredentials;
    }


}
