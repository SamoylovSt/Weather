package com.weather.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegistrationForm> {

    @Override
    public boolean isValid(RegistrationForm form, ConstraintValidatorContext context) {
        if (form.getPassword() == null || form.getRepeatPassword() == null) {
            return true;
        }
        return form.getPassword().equals(form.getRepeatPassword());
    }
}
