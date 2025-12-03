package com.weather.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatch
public class RegistrationForm {

    @NotBlank(message = "The user name cannot be empty.")
    @Size(min = 3, max = 20, message = "Username from 3 to 20 characters")
    private String username;

    @NotBlank(message = "The password cannot be empty.")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Repeat the password")
    @Size(min = 6, message = "The password must be at least 6 characters long")
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
