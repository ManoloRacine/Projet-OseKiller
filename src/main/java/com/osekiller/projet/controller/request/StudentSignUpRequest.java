package com.osekiller.projet.controller.request;

import javax.validation.constraints.NotBlank;

public record StudentSignUpRequest (
        @NotBlank(message = "firstname-is-mandatory") String firstName,
        @NotBlank(message = "lastname-is-mandatory") String lastname,
        @NotBlank(message = "email-is-mandatory") String email,
        @NotBlank(message = "password-is-mandatory") String password) {
}
