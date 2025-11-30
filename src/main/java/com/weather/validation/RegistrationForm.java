package com.weather.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank(message="Имя пользователя не может быть пустым")
    @Size(min=3, max = 20, message = "Имя пользователя от 3-х до 20-ти символов")
    private String username;

    @NotBlank(message="Имя пользователя не может быть пустым")
    @Size(min = 6,message = "Пароль не менее 6-ти символов")
    private String password;

    @NotBlank(message = "Повторите пароль")
    private String repeatPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

}
