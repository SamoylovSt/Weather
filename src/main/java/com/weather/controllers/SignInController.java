package com.weather.controllers;

import com.weather.entity.Session;
import com.weather.service.SessionService;
import com.weather.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.UUID;

@Controller
public class SignInController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/sign-in")
    public String showSignInPage() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         HttpServletResponse response) {
        if (!userService.authenticate(username, password)) {
            return "sign-in-with-errors";
        }
        Session session = sessionService.createSession(userService.findByUsername(username));
        Cookie cookie = sessionService.createCookies(session);
        response.addCookie(cookie);
        return "redirect:/index";
    }


}
