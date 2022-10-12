package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record OfferDto(
        @NotBlank String position,
        @NotNull double salary,
        @NotBlank String startDate,
        @NotBlank String endDate
        ) {
}
