package com.acs576.g2.bowlingleaguescoringapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}
