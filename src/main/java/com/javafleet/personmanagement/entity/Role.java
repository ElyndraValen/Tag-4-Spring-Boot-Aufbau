package com.javafleet.personmanagement.entity;

/**
 * Benutzer-Rollen für Authorization
 * 
 * Diese Rollen werden in Tag 5 für Method Security genutzt
 */
public enum Role {
    /**
     * Standard-Benutzer mit eingeschränkten Rechten
     */
    USER,
    
    /**
     * Administrator mit vollen Rechten
     */
    ADMIN,
    
    /**
     * Moderator mit erweiterten Rechten
     */
    MODERATOR
}
