package com.osekiller.projet.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record UserDto(@NotBlank String email,
                      @NotBlank String name,
                      @NotBlank boolean enabled,
                      @NotBlank Long id,
                      @NotBlank String role) {

}
