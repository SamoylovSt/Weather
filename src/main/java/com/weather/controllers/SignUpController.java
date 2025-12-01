package com.weather.controllers;

import com.weather.validation.RegistrationForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;


@Controller
public class SignUpController {

    @GetMapping("/sign-up")
    public String showSignup() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@Valid RegistrationForm form,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletResponse response,
                               HttpServletRequest request) {
        String mySessionId = UUID.randomUUID().toString();
        Cookie myCookie = new Cookie("session", mySessionId);
//TODO остановился на куки и сессии
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            if (error != null) {
                String message = error.getDefaultMessage();
                model.addAttribute("errorMessage", message);
                return "sign-up-with-errors";
            }
        }
        return "sign-up";
    }
}


//TODO спросить правильно ли делаю валидацию