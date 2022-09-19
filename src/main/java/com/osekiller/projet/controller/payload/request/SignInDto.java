package com.osekiller.projet.controller.payload.request;
import javax.validation.constraints.NotBlank;

public record SignInDto(
    @NotBlank String email,
    @NotBlank String password
) {
}
