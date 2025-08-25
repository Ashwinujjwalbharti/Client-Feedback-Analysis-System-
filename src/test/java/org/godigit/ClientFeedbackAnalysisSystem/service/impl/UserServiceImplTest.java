package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.dto.UserDto;
import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;
import org.godigit.ClientFeedbackAnalysisSystem.mapper.UserMapper;
import org.godigit.ClientFeedbackAnalysisSystem.models.User;
import org.godigit.ClientFeedbackAnalysisSystem.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl service;

    private UserDto userDto(String username, String rawPassword) {
        UserDto dto = new UserDto();
        dto.setUsername(username);
        dto.setPassword(rawPassword);
        return dto;
    }

    private User user(String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        return u;
    }

    @Test
    @DisplayName("saveUser: maps DTO to entity, encodes password, and persists")
    void saveUser_mapsEncodesAndSaves() {
        String username = "alice";
        String rawPass = "plaintext";
        String encodedPass = "ENCODED";
        UserDto dto = userDto(username, rawPass);
        User mappedEntity = user(username, rawPass);
        User savedEntity = user(username, encodedPass);

        when(userMapper.toEntity(dto)).thenReturn(mappedEntity);
        when(passwordEncoder.encode(rawPass)).thenReturn(encodedPass);
        when(userRepository.save(any(User.class))).thenReturn(savedEntity);

        User result = service.saveUser(dto);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(encodedPass, result.getPassword());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User toSave = userCaptor.getValue();
        assertEquals(encodedPass, toSave.getPassword());

        verify(userMapper).toEntity(dto);
        verify(passwordEncoder).encode(rawPass);
        verifyNoMoreInteractions(userMapper, passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("getUser: returns mapped DTO when user exists")
    void getUser_returnsDto() {
        String username = "bob";
        User found = user(username, "ignored");
        UserDto dto = new UserDto();
        dto.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(found));
        when(userMapper.toDTO(found)).thenReturn(dto);

        UserDto result = service.getUser(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());

        verify(userRepository).findByUsername(username);
        verify(userMapper).toDTO(found);
        verifyNoMoreInteractions(userRepository, userMapper);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("getUser: throws RuntimeException('User not found') when user missing")
    void getUser_userNotFound_throws() {
        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getUser("missing"));
        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findByUsername("missing");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper, passwordEncoder);
    }

    // @Test
    // @DisplayName("updateUserRole: updates role (case-insensitive), saves, and returns mapped DTO")
    // void updateUserRole_updatesAndReturnsDto() {
    //     String username = "carol";
    //     User found = user(username, "pass");
    //     found.setRole(List.of(Role.CLIENT));

    //     UserDto dto = new UserDto();
    //     dto.setUsername(username);

    //     when(userRepository.findByUsername(username)).thenReturn(Optional.of(found));
    //     when(userRepository.save(found)).thenReturn(found);
    //     when(userMapper.toDTO(found)).thenReturn(dto);

    //     UserDto result = service.updateUserRole(username, "manager");

    //     assertNotNull(result);
    //     assertEquals(username, result.getUsername());
    //     assertEquals(1, found.getRoles().size());
    //     assertEquals(Role.MANAGER, found.getRoles().get(0));

    //     verify(userRepository).findByUsername(username);
    //     verify(userRepository).save(found);
    //     verify(userMapper).toDTO(found);
    //     verifyNoMoreInteractions(userRepository, userMapper);
    //     verifyNoInteractions(passwordEncoder);
    // }

    // @Test
    // @DisplayName("updateUserRole: throws on invalid role with message 'Invalid role. Supported roles are: ADMIN, MANAGER, CLIENT'")
    // void updateUserRole_invalidRole_throws() {
    //     String username = "dave";
    //     User found = user(username, "pass");
    //     found.setRoles(List.of(Role.CLIENT));

    //     when(userRepository.findByUsername(username)).thenReturn(Optional.of(found));

    //     RuntimeException ex = assertThrows(RuntimeException.class,
    //             () -> service.updateUserRole(username, "owner"));

    //     assertEquals("Invalid role. Supported roles are: ADMIN, MANAGER, CLIENT", ex.getMessage());

    //     verify(userRepository).findByUsername(username);
    //     verifyNoMoreInteractions(userRepository);
    //     verifyNoInteractions(userMapper, passwordEncoder);
    // }

    @Test
    @DisplayName("updateUserRole: throws 'User not found' when username missing")
    void updateUserRole_userMissing_throws() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.updateUserRole("ghost", "admin"));
        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findByUsername("ghost");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper, passwordEncoder);
    }
}
