package com.annieryannel.recommendationsapp.utils.validation;

import com.annieryannel.recommendationsapp.DTO.RegistrationFormDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationFormDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegistrationFormDto dto, ConstraintValidatorContext context) {
        try {
            return dto.getPassword().equals(dto.getPasswordConfirm());
        } catch (NullPointerException e) {
            return true;
        }
    }
}
