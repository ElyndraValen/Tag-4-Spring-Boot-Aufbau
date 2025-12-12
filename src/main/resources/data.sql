-- ========================================
-- Tag 4: Spring Boot Aufbau - Test Data
-- ========================================

-- Test Users mit BCrypt verschlüsselten Passwörtern
-- WICHTIG: Diese Passwörter sind mit BCrypt gehashed!

-- User 1: admin / admin123
-- BCrypt Hash für "admin123"
INSERT INTO users (username, password, role, enabled, account_non_locked)
VALUES ('admin',
        '$2a$10$reRR0tT19vP15KSueYfHCOZtlweRh9E6LgLXKdoYQAlHYfjBN.Pru',
        'ADMIN',
        true,
        true);

-- User 2: user / user123
-- BCrypt Hash für "user123"
INSERT INTO users (username, password, role, enabled, account_non_locked)
VALUES ('user',
        '$2a$10$D20xPPSdLGd5NnTkCzSZ2unTxvE/IRqS5ZAH4PaJp/7d519ccie8m',
        'USER',
        true,
        true);

-- User 3: moderator / mod123
-- BCrypt Hash für "mod123"
INSERT INTO users (username, password, role, enabled, account_non_locked)
VALUES ('moderator',
        '$2a$10$dYczvHnPSVaVEdD0hTXfH.PwYqbby.O/Ik6IbT/kb36o2sep0cl6C',
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
