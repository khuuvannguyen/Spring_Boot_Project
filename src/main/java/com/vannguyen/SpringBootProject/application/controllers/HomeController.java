package com.vannguyen.SpringBootProject.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public String home() {
        return "Home page";
    }
}
