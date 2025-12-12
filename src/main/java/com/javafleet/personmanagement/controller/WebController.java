package com.javafleet.personmanagement.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Web Controller für HTML-Seiten (Thymeleaf)
 *
 * @Controller statt @RestController - gibt HTML zurück, nicht JSON!
 */
@Controller
public class WebController {

    /**
     * Login-Seite anzeigen
     *
     * @return Name des Thymeleaf-Templates (login.html)
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Persons-Übersichtsseite (geschützt!)
     *
     * Nur eingeloggte User können diese Seite sehen
     *
     * @return Name des Thymeleaf-Templates (persons.html)
     */
    @GetMapping("/persons")
    public String persons() {
        return "persons";
    }

    /**
     * Root-URL "/" leitet zu /persons um
     *
     * @return Redirect zur Persons-Seite
     */
    @GetMapping("/")
    public String home() {

        return "redirect:/persons";
    }

    @PostMapping("/ausloggen")
    public String ausloggen(HttpServletRequest request) {
        try {
            //        HttpSession session =request.getSession();
//        session.invalidate();
            request.logout();
        } catch (ServletException ex) {
            System.getLogger(WebController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return "redirect:/login";
    }

}
