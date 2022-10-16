package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ValidationDto(
                @NotNull(message = "validation-is-mandatory") Boolean validation,
                @NotBlank(message = "feedBack-is-mandatory") String feedBack) {
}
