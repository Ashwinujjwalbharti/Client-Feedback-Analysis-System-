//package org.godigit.ClientFeedbackAnalysisSystem.mapper;
//
//import org.godigit.ClientFeedbackAnalysisSystem.dto.UserDto;
//import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;
//import org.godigit.ClientFeedbackAnalysisSystem.models.User;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class UserMapper {
//    public User toEntity(UserDto userDTO) {
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(userDTO.getPassword());
//        userDTO.setRoles(user.getRoles());
//        user.setRoles(List.of(Role.CLIENT)); // default role is CLIENT
//        return user;
//    }
//
//    public UserDto toDTO(User user) {
//        UserDto userDTO = new UserDto();
//        userDTO.setUsername(user.getUsername());
//        userDTO.setPassword(user.getPassword());
//        user.setRoles(userDTO.getRoles()); // Update this line
//        return userDTO;
//    }
//
//    public List<UserDto> toDTOs(List<User> users) {
//        return users.stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//}
package org.godigit.ClientFeedbackAnalysisSystem.mapper;

import org.godigit.ClientFeedbackAnalysisSystem.dto.UserDto;
import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;
import org.godigit.ClientFeedbackAnalysisSystem.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Entity <- DTO (used e.g., during registration)
    public User toEntity(UserDto dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        // Raw password from DTO; encode it in the service layer
        user.setPassword(dto.getPassword());

        // If roles are provided, use them; else default to CLIENT (or move defaulting to the service)
        Role role = (dto.getRole() == null)
                ? (Role.CLIENT)
                : dto.getRole();
        user.setRole(role);

        return user;
    }

    // DTO <- Entity (used for responses)
    public UserDto toDTO(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        // Never expose password on responses
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
