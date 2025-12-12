package com.javafleet.personmanagement.repository;

import com.javafleet.personmanagement.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository für Person Entity
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    Optional<Person> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
