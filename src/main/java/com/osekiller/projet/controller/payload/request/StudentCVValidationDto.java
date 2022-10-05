package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;

public record StudentCVValidationDto(
                boolean validation,
                @NotBlank(message = "feedBack-is-mandatory") String feedBack) {
}
