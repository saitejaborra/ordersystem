package com.techcrunchers.capstoneprojectdbs.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@CrossOrigin(origins="http://localhost:3000/")
public class WelcomeController {
    @GetMapping("/")
    public String welcome() {
        return "Hello World!";
    }
}
