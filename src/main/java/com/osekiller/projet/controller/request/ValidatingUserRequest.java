package com.osekiller.projet.controller.request;

import javax.validation.constraints.NotBlank;

public record ValidatingUserRequest(
        @NotBlank(message = "token-is-mandatory") String token,
        @NotBlank(message = "email-is-mandatory") String email,
        @NotBlank(message = "valid-is-mandatory") boolean valid) {
}
