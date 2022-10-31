package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ApplicationDto(
        @NotBlank String studentName,
        @NotBlank String companyName,
        @NotNull Long offerId,
        @NotBlank String position
) {
}
