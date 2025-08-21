//package org.godigit.ClientFeedbackAnalysisSystem.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.godigit.ClientFeedbackAnalysisSystem.entity.Role;
//
//import java.util.List;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String username;
//    private String password;
//
//    @ElementCollection
//    @Enumerated(EnumType.STRING)
//    private List<Role> roles;
//
//
//}

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
    private String password; // store BCrypt-hashed password

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Column(name = "role", nullable = false, length = 30)
    private List<Role> roles;
}
