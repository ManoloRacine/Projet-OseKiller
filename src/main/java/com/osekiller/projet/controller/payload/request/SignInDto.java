package com.osekiller.projet.controller.payload.request;
import javax.validation.constraints.NotBlank;

public record SignInDto(
    @NotBlank(message = "email-is-mandatory") String email,
    @NotBlank(message = "password-is-mandatory") String password
) {
}
