package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserInfoDto(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank String email
) {
}
