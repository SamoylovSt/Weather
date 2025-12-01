package com.weather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

    @GetMapping("/sign-in")
    public String showSignIn() {
        return "sign-in";
    }

}
