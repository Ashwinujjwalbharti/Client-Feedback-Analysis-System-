package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.dto.UpdateRoleRequest;
import org.godigit.ClientFeedbackAnalysisSystem.dto.UserDto;
import org.godigit.ClientFeedbackAnalysisSystem.models.User;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping("/users/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PutMapping("/users/{username}/role")
    public UserDto updateUserRole(@PathVariable String username, @RequestBody UpdateRoleRequest request) {
        if (request.getRole() == null || request.getRole().isEmpty()) {
            throw new RuntimeException("Role is required");
        }
        return userService.updateUserRole(username, request.getRole());
    }
}
