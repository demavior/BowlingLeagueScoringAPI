package com.acs576.g2.bowlingleaguescoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserCredentialDTO {
    private Long id;
    private String email;
    private String password;
    private String currentPassword;
    private String newPassword;
    private String role;
    private Set<RoleDto> roles;
}
