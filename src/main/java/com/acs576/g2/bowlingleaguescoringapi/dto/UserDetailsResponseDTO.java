package com.acs576.g2.bowlingleaguescoringapi.dto;

import com.acs576.g2.bowlingleaguescoringapi.constants.RoleName;
import com.acs576.g2.bowlingleaguescoringapi.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDTO {
    private Long id;
    private String email;
    private short age;
    private Set<String> role;
    private String firstName;
    private String lastName;
    private String middleName;
}
