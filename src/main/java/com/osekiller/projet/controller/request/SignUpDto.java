package com.osekiller.projet.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record SignUpDto(
        @NotBlank(message = "name-is-mandatory") String name,
        @NotBlank(message = "email-is-mandatory") @Email String email,
        @NotBlank(message = "password-is-mandatory") String password,
        @NotBlank(message = "role-is-mandatory") String role
) {
}
