-- ========================================
-- Tag 4: Spring Boot Aufbau - Test Data
-- ========================================

-- Test Users mit BCrypt verschlüsselten Passwörtern
-- WICHTIG: Diese Passwörter sind mit BCrypt gehashed!

-- User 1: admin / admin123
-- BCrypt Hash für "admin123"
INSERT INTO users (username, password, role, enabled, account_non_locked) 
VALUES ('admin', 
        '$2a$10$ubb0Y9V6rqnr0guIveb.C.B11g9WpfyQG3b4elPzG.422tXCEJG6O', 
        'ADMIN', 
        true, 
        true);

-- User 2: user / user123
-- BCrypt Hash für "user123"
INSERT INTO users (username, password, role, enabled, account_non_locked) 
VALUES ('user', 
        '$2a$10$YS5xnBkTO./uBHU43kRKpOu9wQRz8uKLpmeEizYJwwOGFB2MUxmyO', 
        'USER', 
        true, 
        true);

-- User 3: moderator / mod123
-- BCrypt Hash für "mod123"
INSERT INTO users (username, password, role, enabled, account_non_locked) 
VALUES ('moderator', 
        '$2a$10$FzKnaE/r7.KCDrSqNiroy.7mUJlaAmCOGHU4fAFpTQbs7hQkw9Uqa', 
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
