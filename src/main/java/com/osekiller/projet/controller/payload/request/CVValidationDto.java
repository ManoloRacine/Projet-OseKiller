package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;

public record CVValidationDto(
        @NotBlank(message = "validate-is-mandatory") boolean validate,
        @NotBlank(message = "feedBack-is-mandatory") String feedBack
) {
}
