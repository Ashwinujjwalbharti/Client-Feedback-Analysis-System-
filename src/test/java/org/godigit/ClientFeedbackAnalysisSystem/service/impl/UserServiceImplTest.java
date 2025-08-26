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

    private UserDto dto(String username, String password, Role role) {
        return new UserDto(username, password, role);
    }

    private User user(String username, String password, Role role) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);
        return u;
    }

    @Test
    @DisplayName("saveUser: maps DTO -> entity, encodes password, saves, returns saved entity")
    void saveUser_encodesPasswordAndSaves() {
        UserDto inputDto = dto("john", "rawPass", Role.CLIENT);
        User mappedEntity = user("john", "rawPass", Role.CLIENT);
        User savedEntity = user("john", "encodedPass", Role.CLIENT);

        when(userMapper.toEntity(inputDto)).thenReturn(mappedEntity);
        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(savedEntity);

        User result = service.saveUser(inputDto);

        assertSame(savedEntity, result, "Should return the entity returned by repository");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).toEntity(inputDto);
        verify(passwordEncoder).encode("rawPass");
        verify(userRepository).save(captor.capture());

        User captured = captor.getValue();
        assertEquals("john", captured.getUsername());
        assertEquals("encodedPass", captured.getPassword(), "Password must be encoded before saving");
        assertEquals(Role.CLIENT, captured.getRole(), "Role should be preserved from DTO->entity mapping");

        verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);
    }

    @Test
    @DisplayName("getUser: finds by username and maps to UserDto")
    void getUser_found_returnsDto() {
        User entity = user("alice", "secret", Role.MANAGER);
        UserDto expectedDto = dto("alice", "secret", Role.MANAGER);

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(entity));
        when(userMapper.toDTO(entity)).thenReturn(expectedDto);

        UserDto result = service.getUser("alice");

        assertSame(expectedDto, result, "Service should return mapper's DTO instance");
        verify(userRepository).findByUsername("alice");
        verify(userMapper).toDTO(entity);
        verifyNoMoreInteractions(userRepository, userMapper);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("getUser: throws RuntimeException when user not found")
    void getUser_notFound_throws() {
        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getUser("missing"));
        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findByUsername("missing");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper, passwordEncoder);
    }

    @Test
    @DisplayName("updateUserRole: updates role (case-insensitive) and returns mapped DTO")
    void updateUserRole_validRole_caseInsensitive() {
        User entity = user("bob", "pw", Role.CLIENT);

        when(userRepository.findByUsername("bob")).thenReturn(Optional.of(entity));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        when(userMapper.toDTO(entity)).thenReturn(dto("bob", "pw", Role.ADMIN));

        UserDto result = service.updateUserRole("bob", "aDmIn");

        assertEquals(Role.ADMIN, entity.getRole(), "Entity role should be updated to ADMIN");
        assertEquals(Role.ADMIN, result.getRole(), "DTO role should reflect updated role");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).findByUsername("bob");
        verify(userRepository).save(captor.capture());
        verify(userMapper).toDTO(entity);

        assertEquals(Role.ADMIN, captor.getValue().getRole());
        verifyNoMoreInteractions(userRepository, userMapper);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("updateUserRole: throws when role is invalid")
    void updateUserRole_invalidRole_throws() {
        User entity = user("carol", "pw", Role.CLIENT);
        when(userRepository.findByUsername("carol")).thenReturn(Optional.of(entity));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.updateUserRole("carol", "superuser"));

        assertEquals("Invalid role. Supported roles are: ADMIN, MANAGER, CLIENT", ex.getMessage());

        verify(userRepository).findByUsername("carol");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper, passwordEncoder);
    }

    @Test
    @DisplayName("updateUserRole: throws when user not found")
    void updateUserRole_userNotFound_throws() {
        when(userRepository.findByUsername("dave")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.updateUserRole("dave", "manager"));

        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findByUsername("dave");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper, passwordEncoder);
    }

    @Test
    @DisplayName("updateUserRole: supports ADMIN, MANAGER, CLIENT (case-insensitive)")
    void updateUserRole_allValidRoles() {
        User u1 = user("u1", "pw", Role.CLIENT);
        when(userRepository.findByUsername("u1")).thenReturn(Optional.of(u1));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        when(userMapper.toDTO(u1)).thenReturn(dto("u1", "pw", Role.ADMIN));
        UserDto dto1 = service.updateUserRole("u1", "ADMIN");
        assertEquals(Role.ADMIN, u1.getRole());
        assertEquals(Role.ADMIN, dto1.getRole());

        User u2 = user("u2", "pw", Role.CLIENT);
        when(userRepository.findByUsername("u2")).thenReturn(Optional.of(u2));
        when(userMapper.toDTO(u2)).thenReturn(dto("u2", "pw", Role.MANAGER));
        UserDto dto2 = service.updateUserRole("u2", "manager");
        assertEquals(Role.MANAGER, u2.getRole());
        assertEquals(Role.MANAGER, dto2.getRole());

        User u3 = user("u3", "pw", Role.ADMIN);
        when(userRepository.findByUsername("u3")).thenReturn(Optional.of(u3));
        when(userMapper.toDTO(u3)).thenReturn(dto("u3", "pw", Role.CLIENT));
        UserDto dto3 = service.updateUserRole("u3", "Client");
        assertEquals(Role.CLIENT, u3.getRole());
        assertEquals(Role.CLIENT, dto3.getRole());

        verify(userRepository, times(3)).save(any(User.class));
    }
}
