package org.godigit.ClientFeedbackAnalysisSystem.dto;

import org.godigit.ClientFeedbackAnalysisSystem.models.enums.Role;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private String username;
    private String password;
    private Role role;
}
