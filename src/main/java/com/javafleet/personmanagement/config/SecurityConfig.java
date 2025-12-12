package com.javafleet.personmanagement.config;

import com.javafleet.personmanagement.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Konfiguration
 * 
 * Diese Klasse konfiguriert:
 * - PasswordEncoder (BCrypt)
 * - SecurityFilterChain (Login, Logout, Authorization)
 * - Remember-Me Funktionalität
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final CustomUserDetailsService userDetailsService;
    
    /**
     * PasswordEncoder Bean für BCrypt Password Hashing
     * 
     * BCrypt ist langsam by design - schützt vor Brute-Force Attacken
     * Jedes Passwort bekommt automatisch einen eigenen Salt
     * 
     * @return BCryptPasswordEncoder mit Default Cost-Factor (10)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * SecurityFilterChain konfiguriert alle Security-Regeln
     * 
     * Diese Bean ersetzt die alte WebSecurityConfigurerAdapter Methode
     * 
     * @param http HttpSecurity Builder
     * @return Konfigurierte SecurityFilterChain
     * @throws Exception bei Konfigurationsfehlern
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Authorization: Alle Requests brauchen Authentication
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            
            // Form-basiertes Login
            .formLogin(form -> form
                .loginPage("/login")  // Custom Login-Page URL
                .defaultSuccessUrl("/persons", true)  // Nach Login → /persons
                .permitAll()  // Login-Page ist öffentlich zugänglich
            )
            
            // Logout Konfiguration
            .logout(logout -> logout
                .logoutUrl("/logout")  // POST zu /logout für Logout
                .logoutSuccessUrl("/login?logout")  // Nach Logout → Login mit Nachricht
                .permitAll()
            )
            
            // Remember-Me Funktionalität
            .rememberMe(remember -> remember
                .key("mySecretRememberMeKey")  // Geheimer Key für Remember-Me Token
                .tokenValiditySeconds(86400 * 30)  // 30 Tage gültig
                .rememberMeParameter("remember-me")  // Name des Form-Parameters
                .userDetailsService(userDetailsService)  // Unser CustomUserDetailsService
            )
            
            // HTTP Basic Authentication (für API-Tests mit curl)
            .httpBasic(basic -> {});
        
        return http.build();
    }
}
