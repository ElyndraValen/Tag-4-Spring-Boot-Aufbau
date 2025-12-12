package com.javafleet.personmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity für Spring Security Authentication
 * 
 * Diese Entity speichert Benutzer-Informationen in der Datenbank
 * Passwörter werden mit BCrypt verschlüsselt gespeichert
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username darf nicht leer sein")
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @NotBlank(message = "Password darf nicht leer sein")
    @Column(nullable = false)
    private String password;  // BCrypt encrypted!
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role = Role.USER;
    
    @Column(nullable = false)
    private boolean enabled = true;
    
    @Column(nullable = false)
    private boolean accountNonLocked = true;
}
