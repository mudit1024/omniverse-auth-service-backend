package com.omniverse.auth_Service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        //testing jwt check
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return "Hello OmniVerse🚀 "+email+" you are authenticated";
    }
}
