package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;

public record JwtResponseDto(
        @NotBlank String accessToken,
        @NotBlank String refreshToken,
        @NotBlank String tokenType
) {
}
