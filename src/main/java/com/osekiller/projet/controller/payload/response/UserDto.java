package com.osekiller.projet.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
public record UserDto(@NotBlank String email,
                      @NotBlank String name,
                      @NotBlank boolean enabled,
                      @NotBlank Long id,
                      @NotBlank String role,
                      boolean cvValidated,
                      boolean cvRejected) {

}
