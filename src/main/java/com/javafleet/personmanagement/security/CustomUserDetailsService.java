package com.javafleet.personmanagement.security;

import com.javafleet.personmanagement.entity.User;
import com.javafleet.personmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Custom UserDetailsService für Database-basierte Authentication
 * 
 * Diese Klasse wird von Spring Security automatisch verwendet wenn ein User sich einloggt
 * Die loadUserByUsername() Methode lädt den User aus der Datenbank
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    /**
     * Lädt einen User aus der Datenbank für Spring Security
     * 
     * Diese Methode wird von Spring Security beim Login automatisch aufgerufen
     * 
     * @param username Der Username vom Login-Formular
     * @return UserDetails-Objekt für Spring Security
     * @throws UsernameNotFoundException wenn User nicht existiert
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user from database: {}", username);
        
        // User aus Datenbank laden
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + username));
        
        log.info("User found: {} with role: {}", user.getUsername(), user.getRole());
        
        // Spring Security's UserDetails Objekt erstellen
        // WICHTIG: Das ist NICHT unsere User-Entity, sondern Spring Security's User-Klasse!
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),  // BCrypt encrypted!
                user.isEnabled(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                user.isAccountNonLocked(),
                getAuthorities(user)
        );
    }
    
    /**
     * Konvertiert unsere Role in Spring Security's GrantedAuthority
     * 
     * Spring Security erwartet Authorities mit "ROLE_" Prefix
     * 
     * @param user Der User aus unserer Datenbank
     * @return Collection von GrantedAuthority
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
}
