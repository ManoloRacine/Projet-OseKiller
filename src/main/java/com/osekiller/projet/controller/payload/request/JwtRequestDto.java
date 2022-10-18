package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;

public record JwtRequestDto(
        @NotBlank(message = "refreshToken-is-mandatory") String refreshToken
) {
}
