package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;

public record UsersDto(@NotBlank String email,
                       @NotBlank String name,
                       @NotBlank boolean enabled) {

}
