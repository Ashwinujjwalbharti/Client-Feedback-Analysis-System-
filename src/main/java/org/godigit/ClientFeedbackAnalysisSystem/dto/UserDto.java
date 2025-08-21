package org.godigit.ClientFeedbackAnalysisSystem.dto;

import lombok.*;
import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter@ToString
public class UserDto {
    private String username;
    private String password;
    private List<Role> roles;

}
