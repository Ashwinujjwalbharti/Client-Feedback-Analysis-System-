package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.dto.UserDto;
import org.godigit.ClientFeedbackAnalysisSystem.models.User;

public interface UserService {
    User saveUser(UserDto userDto);

    UserDto getUser(String username);

    UserDto updateUserRole(String username, String role);
}
