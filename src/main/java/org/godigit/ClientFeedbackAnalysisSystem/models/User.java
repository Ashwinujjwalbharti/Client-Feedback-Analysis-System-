package org.godigit.ClientFeedbackAnalysisSystem.models;

import org.godigit.ClientFeedbackAnalysisSystem.models.enums.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_username", columnList = "username", unique = true)
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String password; 
    
    @Column(name = "role", nullable = false, length = 30)
    private Role role;
}
