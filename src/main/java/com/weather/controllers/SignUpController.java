package com.weather.controllers;

import com.weather.service.SessionService;
import com.weather.service.UserService;
import com.weather.validation.RegistrationForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;


@Controller
public class SignUpController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @GetMapping("/sign-up")
    public String showSignup() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@Valid RegistrationForm form,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletResponse response) {
        String username = form.getUsername();
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("session", sessionId);
        // что ещё добавить в куку?
        //TODO настроить удаление сессии по истечению
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            if (error != null) {
                String message = error.getDefaultMessage();
                model.addAttribute("errorMessage", message);
                return "sign-up-with-errors";
            }
        }
        if (userService.existByUsername(username)) {
            model.addAttribute("errorMessage", "User already exist");
            return "sign-up-with-errors";
        }
        response.addCookie(cookie);
        userService.createUser(username, form.getPassword(), sessionId);//регистрацию в трайкетч?
        return "sign-up";
    }
}