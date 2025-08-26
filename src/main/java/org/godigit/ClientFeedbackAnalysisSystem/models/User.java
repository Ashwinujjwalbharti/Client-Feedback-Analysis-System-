package org.godigit.ClientFeedbackAnalysisSystem.models;

import jakarta.persistence.*;
import lombok.*;
import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;

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
