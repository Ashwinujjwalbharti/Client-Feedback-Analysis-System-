package org.godigit.ClientFeedbackAnalysisSystem.models;

import jakarta.persistence.*;
import lombok.*;
import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles;


}