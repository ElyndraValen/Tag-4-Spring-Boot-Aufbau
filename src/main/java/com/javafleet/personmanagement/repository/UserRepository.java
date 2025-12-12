package com.javafleet.personmanagement.repository;

import com.javafleet.personmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository für User Entity
 * 
 * Spring Data JPA erstellt automatisch die Implementierung
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Findet einen User anhand des Usernames
     * 
     * Query: SELECT * FROM users WHERE username = ?
     * 
     * @param username Der Username
     * @return Optional mit User oder empty wenn nicht gefunden
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Prüft ob ein Username bereits existiert
     * 
     * Query: SELECT COUNT(*) > 0 FROM users WHERE username = ?
     * 
     * @param username Der zu prüfende Username
     * @return true wenn Username existiert, sonst false
     */
    boolean existsByUsername(String username);
}
