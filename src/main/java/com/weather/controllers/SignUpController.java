package com.weather.controllers;

import com.weather.validation.RegistrationForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class SignUpController {

    @GetMapping("/sign-up")
    public String showSignup() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@Valid RegistrationForm form,
                               BindingResult bindingResult,
                               Model model) {


        if (bindingResult.hasErrors()) {
            System.out.println("Найдены ошибки валидации:");
            model.addAttribute("passwordError",true);
            //TODO тут остановился
            return "sign-up-with-errors";

        } else {
            System.out.println("Ошибок валидации НЕТ");
        }

        return "sign-up";
    }
}
//        //TODO хибернейт валидатор
//        //TODO какой лучше порядок написания круда?
//         TODO пробелы в понимании форм