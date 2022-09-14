package com.osekiller.projet.controller.request;

import javax.validation.constraints.NotBlank;

public record CompanySignUpRequest(
        @NotBlank(message = "companyName-is-mandatory") String companyName,
        @NotBlank(message = "email-is-mandatory") String email,
        @NotBlank(message = "password-is-mandatory") String password) {
}
