package com.weather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {

    @GetMapping("/sign-in")
    public String showSignInPage() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        System.out.println(username + " " + password);
        //проверить есть ли такой юзер
        //если есть создать сессию и куку
        //куку отдать в блаузер, сессию сохранить
        //перенаправить на главную
        //таймлиф
        return "sign-in";
    }

}
