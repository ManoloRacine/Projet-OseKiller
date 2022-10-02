package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;

public record StudentCVValidationDto(
        @NotBlank(message = "validation-is-mandatory") boolean validation,
        @NotBlank(message = "feedBack-is-mandatory") String feedBack
) {
}
