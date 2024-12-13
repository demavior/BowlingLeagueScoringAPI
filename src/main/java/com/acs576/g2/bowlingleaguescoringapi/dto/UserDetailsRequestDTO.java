package com.acs576.g2.bowlingleaguescoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDetailsRequestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
}
