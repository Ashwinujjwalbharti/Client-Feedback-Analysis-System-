package org.godigit.ClientFeedbackAnalysisSystem.dto;

import org.springframework.stereotype.Component;

@Component
public class UpdateRoleRequest {
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
