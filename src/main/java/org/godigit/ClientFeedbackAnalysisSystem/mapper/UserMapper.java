package org.godigit.ClientFeedbackAnalysisSystem.mapper;

import org.godigit.ClientFeedbackAnalysisSystem.dto.UserDto;
import org.godigit.ClientFeedbackAnalysisSystem.models.User;
import org.godigit.ClientFeedbackAnalysisSystem.models.enums.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserDto dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        
        Role role = (dto.getRole() == null)
                ? (Role.CLIENT)
                : dto.getRole();
        user.setRole(role);

        return user;
    }

    public UserDto toDTO(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(null);
        dto.setRole(user.getRole());

        return dto;
    }

    public List<UserDto> toDTOs(List<User> users) {
        if (users == null) return List.of();
        return users.stream()
                .filter(Objects::nonNull)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
