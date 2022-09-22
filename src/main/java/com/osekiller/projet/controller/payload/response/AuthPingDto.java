package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;

public record AuthPingDto(
        @NotBlank String email,
        @NotBlank String role,
        @NotBlank String userName
) {
}
