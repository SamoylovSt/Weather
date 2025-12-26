package com.weather.controllers;

import com.weather.entity.Session;
import com.weather.entity.User;
import com.weather.service.SessionService;
import com.weather.service.UserService;
import com.weather.validation.RegistrationForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


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
        if (!form.getPassword().equals(form.getRepeatPassword())) {
            bindingResult.reject("errorMessage", "Passwords don't match");
        }
        if (userService.existByUsername(username)) {
            model.addAttribute("errorMessage", "User already exist");
            return "sign-up-with-errors";
        }
        User newUser = userService.createUser(username, form.getPassword());
        Session newSession = sessionService.createSession(newUser);
        Cookie cookie = sessionService.createCookies(newSession);
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            if (error != null) {
                String message = error.getDefaultMessage();
                model.addAttribute("errorMessage", message);
                return "sign-up-with-errors";
            }
        }
        response.addCookie(cookie);
        return "redirect:/index";
    }
}