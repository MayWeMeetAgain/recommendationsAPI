package com.annieryannel.recommendationsapp.DTO;

import com.annieryannel.recommendationsapp.utils.validation.PasswordMatches;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@PasswordMatches
public class RegistrationFormDto {

        @NotBlank(message = "username must not be blank")
        private String username;

        @NotBlank(message = "email must not be blank")
        @Email(message = "email must use pattern name@domen.com")
        private  String email;

        @NotBlank(message = "password must not be blank")
        private String password;

        @NotBlank(message = "password confirmation must not be blank")
        private String passwordConfirm;
}
