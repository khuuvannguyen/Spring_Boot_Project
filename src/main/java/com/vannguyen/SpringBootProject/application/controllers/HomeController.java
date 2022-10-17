package com.vannguyen.SpringBootProject.application.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/", "/home"})
public class HomeController {
    @GetMapping
    public String home() {
        return "Home page";
    }
}
