package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;

public record NameAndEmailDto(
        @NotBlank String name,
        @NotBlank String email
) {
}
