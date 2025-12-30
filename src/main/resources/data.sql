-- ========================================
-- Tag 4: Spring Boot Aufbau - Test Data
-- ========================================

-- Test Users mit BCrypt verschlüsselten Passwörtern
-- WICHTIG: Diese Passwörter sind mit BCrypt gehashed!

-- User 1: admin / admin123
-- BCrypt Hash für "admin123"
INSERT INTO users (username, password, role, enabled, account_non_locked) 
VALUES ('admin', 
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
        'ADMIN', 
        true, 
        true);

-- User 2: user / user123
-- BCrypt Hash für "user123"
INSERT INTO users (username, password, role, enabled, account_non_locked) 
VALUES ('user', 
        '$2a$10$VEjxo0jq2YT4jYVd1.OB7uONEp0eVGvGg3oMABpRNKF5m2q5F8fMm', 
        'USER', 
        true, 
        true);

-- User 3: moderator / mod123
-- BCrypt Hash für "mod123"
INSERT INTO users (username, password, role, enabled, account_non_locked) 
VALUES ('moderator', 
        '$2a$10$8Jmv4h5Lp9bKF2mQ7nNz4OqKvB1xY8wZ5tR3cN6aJ7dH2fG9eL1sK', 
        'MODERATOR', 
        true, 
        true);

-- Test Persons für die API
INSERT INTO persons (firstname, lastname, email) 
VALUES ('Max', 'Mustermann', 'max.mustermann@example.com');

INSERT INTO persons (firstname, lastname, email) 
VALUES ('Erika', 'Musterfrau', 'erika.musterfrau@example.com');

INSERT INTO persons (firstname, lastname, email) 
VALUES ('John', 'Doe', 'john.doe@example.com');

INSERT INTO persons (firstname, lastname, email) 
VALUES ('Jane', 'Smith', 'jane.smith@example.com');

-- ========================================
-- Hinweis: So erstellst du eigene BCrypt Hashes
-- ========================================
-- 
-- In Java:
-- BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
-- String hash = encoder.encode("meinPasswort");
-- System.out.println(hash);
--
-- Online Tools (für Testing):
-- https://bcrypt-generator.com/
-- 
-- NIEMALS plain text Passwörter in Production verwenden!
-- ========================================
