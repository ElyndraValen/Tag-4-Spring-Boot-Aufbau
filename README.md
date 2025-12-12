# Tag 4: Spring Boot Aufbau - Spring Security Part 1

**Person Management System mit Spring Security Authentication**

Von Elyndra Valen, Senior Entwicklerin bei Java Fleet Systems Consulting

---

## 📋 Projekt-Übersicht

Dieses Projekt ist Teil des **Spring Boot Aufbau-Kurses** (Tag 4 von 10) und demonstriert die Integration von **Spring Security** mit **Database Authentication**, **BCrypt Password Encoding** und **Session-based Login**.

### Was du in diesem Projekt lernst:

- ✅ Spring Security Integration
- ✅ User-Verwaltung mit JPA Entities
- ✅ BCrypt Password Encryption
- ✅ UserDetailsService Implementation
- ✅ SecurityFilterChain Konfiguration
- ✅ Form-basiertes Login mit Thymeleaf
- ✅ Remember-Me Funktionalität
- ✅ Session Management

---

## 🚀 Quick Start

### Voraussetzungen

- Java JDK 17 oder höher
- Maven 3.6+
- IDE deiner Wahl (IntelliJ IDEA, Eclipse, NetBeans, VS Code)

### Projekt starten

```bash
# 1. Repository klonen oder ZIP entpacken
cd Tag-4-Spring-Boot-Aufbau

# 2. Maven Dependencies laden
mvn clean install

# 3. Anwendung starten
mvn spring-boot:run

# 4. Browser öffnen
http://localhost:8080
```

### Login-Daten

Das Projekt kommt mit vorkonfigurierten Test-Usern:

| Username | Passwort | Rolle |
|----------|----------|-------|
| `admin` | `admin123` | ADMIN |
| `user` | `user123` | USER |
| `moderator` | `mod123` | MODERATOR |

---

## 📁 Projekt-Struktur

```
Tag-4-Spring-Boot-Aufbau/
│
├── src/
│   ├── main/
│   │   ├── java/com/javafleet/personmanagement/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java           # Spring Security Konfiguration
│   │   │   ├── controller/
│   │   │   │   ├── WebController.java            # HTML-Seiten Controller
│   │   │   │   └── PersonRestController.java     # REST API Controller
│   │   │   ├── entity/
│   │   │   │   ├── User.java                     # User Entity (Security)
│   │   │   │   ├── Role.java                     # Role Enum
│   │   │   │   └── Person.java                   # Person Entity (Business)
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java           # User Repository
│   │   │   │   └── PersonRepository.java         # Person Repository
│   │   │   ├── security/
│   │   │   │   └── CustomUserDetailsService.java # UserDetailsService Implementation
│   │   │   └── PersonManagementApplication.java  # Main Class
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── login.html                    # Login-Seite
│   │       │   └── persons.html                  # Persons-Übersicht
│   │       ├── application.properties            # Konfiguration
│   │       └── data.sql                          # Test-Daten
│   │
│   └── test/
│       └── java/com/javafleet/personmanagement/
│           └── (Tests folgen in späteren Tags)
│
├── pom.xml                                        # Maven Dependencies
└── README.md                                      # Diese Datei
```

---

## 🔐 Security Features

### 1. UserDetailsService Pattern

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        // User aus Datenbank laden
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(...));
        
        // Spring Security UserDetails Objekt erstellen
        return new org.springframework.security.core.userdetails.User(...);
    }
}
```

**Was passiert hier?**
- Spring Security ruft beim Login automatisch `loadUserByUsername()` auf
- Wir laden den User aus unserer Datenbank
- Wir konvertieren unsere `User`-Entity in Spring Security's `UserDetails`-Objekt

### 2. BCrypt Password Encoding

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

**Warum BCrypt?**
- ✅ Langsam by design - schützt vor Brute-Force
- ✅ Automatisches Salting - jedes Passwort bekommt eigenen Salt
- ✅ Bewährt seit 1999 - Battle-tested
- ✅ Konfigurierbare Kosten (2^10 bis 2^31 Iterationen)

### 3. SecurityFilterChain Konfiguration

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/persons", true)
        )
        .logout(...)
        .rememberMe(...);
    return http.build();
}
```

**Features:**
- ✅ Alle Requests brauchen Authentication
- ✅ Custom Login-Page unter `/login`
- ✅ Remember-Me für 30 Tage
- ✅ HTTP Basic Auth für API-Tests

---

## 🗄️ Datenbank-Konfiguration

Das Projekt ist standardmäßig mit **H2 In-Memory Database** konfiguriert für schnellen Start.

### H2 Database (Default)

```properties
spring.datasource.url=jdbc:h2:mem:persondb
spring.h2.console.enabled=true
```

**H2 Console:** http://localhost:8080/h2-console

**JDBC URL:** `jdbc:h2:mem:persondb`  
**Username:** `sa`  
**Passwort:** *(leer)*

### MariaDB (Alternative)

Für Production oder persistente Daten kannst du auf MariaDB umstellen:

1. **Kommentiere H2 aus** in `application.properties`
2. **Aktiviere MariaDB:**

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/persondb
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

3. **Erstelle die Datenbank:**

```sql
CREATE DATABASE persondb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

---

## 🧪 Testing

### Browser-Tests

**1. Login-Seite testen:**
```
http://localhost:8080
→ Automatischer Redirect zu /login
```

**2. Mit admin einloggen:**
- Username: `admin`
- Passwort: `admin123`
- Optional: "Angemeldet bleiben" aktivieren

**3. Persons-Seite sehen:**
```
Nach Login → Redirect zu /persons
Zeigt: Willkommens-Nachricht, Username, Rolle, Personen-Tabelle
```

**4. Logout testen:**
```
Klick auf "Abmelden" → Redirect zu /login?logout
```

**5. Remember-Me testen:**
- Login mit "Angemeldet bleiben" aktiviert
- Browser schließen
- Browser neu öffnen und http://localhost:8080 aufrufen
- Du bist noch eingeloggt! 🎉

### API-Tests (curl)

**Mit HTTP Basic Authentication:**

```bash
# GET alle Persons
curl -u admin:admin123 http://localhost:8080/api/persons

# GET Person mit ID 1
curl -u admin:admin123 http://localhost:8080/api/persons/1

# POST neue Person
curl -u admin:admin123 \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"firstname":"Test","lastname":"User","email":"test@example.com"}' \
  http://localhost:8080/api/persons

# DELETE Person mit ID 1
curl -u admin:admin123 \
  -X DELETE \
  http://localhost:8080/api/persons/1
```

**Ohne Authentication (sollte 401 geben):**

```bash
curl http://localhost:8080/api/persons
# Response: 401 Unauthorized
```

---

## 🐛 Troubleshooting

### Problem: "401 Unauthorized" bei allen Requests

**Ursache:** Spring Security ist aktiv und schützt alle Endpoints.

**Lösung:** Das ist korrekt! Du musst dich einloggen.

### Problem: "User not found" beim Login

**Ursache:** Test-User wurden nicht in Datenbank eingefügt.

**Lösung:** 
- Überprüfe ob `data.sql` ausgeführt wurde
- Schau in die Logs: `Loading user from database: admin`
- Öffne H2-Console und prüfe: `SELECT * FROM users;`

### Problem: Passwort stimmt nicht

**Ursache:** BCrypt-Hash in `data.sql` ist falsch.

**Lösung:**
```java
// Eigenen Hash generieren:
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
System.out.println(encoder.encode("meinPasswort"));
```

### Problem: SecurityConfig wird nicht gefunden

**Ursache:** `@Configuration` oder `@EnableWebSecurity` fehlt.

**Lösung:** Überprüfe die Annotations in `SecurityConfig.java`

### Problem: Login-Seite zeigt nur Fehler

**Ursache:** Thymeleaf-Template nicht gefunden.

**Lösung:** 
- Überprüfe `src/main/resources/templates/login.html` existiert
- Überprüfe `spring-boot-starter-thymeleaf` Dependency in `pom.xml`

---

## 📚 Weitere Ressourcen

### Offizielle Dokumentation

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Boot Security](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.security)
- [BCrypt Documentation](https://github.com/spring-projects/spring-security/blob/main/crypto/src/main/java/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.java)

### Blogbeitrag

Dieser Code ist Teil des Blogbeitrags:  
**"Tag 4: Spring Security - Authentication & UserDetailsService"**

👉 [Zum vollständigen Blogbeitrag](https://java-developer.online)

### Nächster Kurstag

**Tag 5: Spring Security - Part 2: Authorization & Method Security**

Was du morgen lernst:
- Role-Based Access Control (RBAC)
- URL-basierte Authorization (hasRole, hasAuthority)
- Method Security mit @PreAuthorize
- JWT statt Session-based Authentication

---

## 🤝 Kontakt & Support

**Fragen oder Probleme?**

- 📧 Email: elyndra@java-developer.online
- 💬 GitHub Issues: [Issue erstellen](#)
- 📚 Blog: https://java-developer.online

---

## 📝 Lizenz

Dieses Projekt ist Teil des Java Fleet Systems Consulting Kursmaterials.  
Frei verwendbar für Lernzwecke.

---

**"Security ist kein Feature - es ist eine Grundvoraussetzung!"**  
*- Elyndra Valen*

Keep coding, keep learning! 💙
