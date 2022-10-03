package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;

public record StudentDto(@NotBlank String email,
                         @NotBlank String name,
                         @NotBlank Long id,
                         @NotBlank boolean enabled,
                         @NotBlank boolean cvValidated,
                         @NotBlank boolean cvRejected,
                         @NotBlank boolean cvPresent,
                         @NotBlank String feedback) {

}
