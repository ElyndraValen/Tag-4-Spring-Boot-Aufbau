package com.javafleet.personmanagement.controller;

import com.javafleet.personmanagement.entity.Person;
import com.javafleet.personmanagement.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST API Controller für Person-Verwaltung
 * 
 * Alle Endpoints sind durch Spring Security geschützt!
 */
@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonRestController {
    
    private final PersonRepository personRepository;
    
    /**
     * Alle Personen abrufen
     * 
     * GET /api/persons
     * 
     * @return Liste aller Personen
     */
    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
    
    /**
     * Person nach ID abrufen
     * 
     * GET /api/persons/{id}
     * 
     * @param id Person-ID
     * @return Person oder 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Neue Person erstellen
     * 
     * POST /api/persons
     * 
     * @param person Person-Daten (validated)
     * @return Erstellte Person
     */
    @PostMapping
    public Person createPerson(@Valid @RequestBody Person person) {
        return personRepository.save(person);
    }
    
    /**
     * Person aktualisieren
     * 
     * PUT /api/persons/{id}
     * 
     * @param id Person-ID
     * @param personDetails Aktualisierte Daten
     * @return Aktualisierte Person oder 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody Person personDetails) {
        
        return personRepository.findById(id)
                .map(person -> {
                    person.setFirstname(personDetails.getFirstname());
                    person.setLastname(personDetails.getLastname());
                    person.setEmail(personDetails.getEmail());
                    return ResponseEntity.ok(personRepository.save(person));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Person löschen
     * 
     * DELETE /api/persons/{id}
     * 
     * @param id Person-ID
     * @return 204 No Content oder 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
