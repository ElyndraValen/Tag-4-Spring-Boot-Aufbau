package com.javafleet.personmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person Entity aus den vorherigen Tagen
 * 
 * Diese Entity wird von unserem REST API verwendet
 */
@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Firstname is required")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String firstname;
    
    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String lastname;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;
}
