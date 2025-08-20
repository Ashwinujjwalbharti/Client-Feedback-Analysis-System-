package org.godigit.ClientFeedbackAnalysisSystem.mapper;

import org.godigit.ClientFeedbackAnalysisSystem.dto.UserDto;
import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;
import org.godigit.ClientFeedbackAnalysisSystem.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(UserDto userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userDTO.setRoles(user.getRoles());
        user.setRoles(List.of(Role.CLIENT)); // default role is CLIENT
        return user;
    }

    public UserDto toDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        user.setRoles(userDTO.getRoles()); // Update this line
        return userDTO;
    }

    public List<UserDto> toDTOs(List<User> users) {
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
