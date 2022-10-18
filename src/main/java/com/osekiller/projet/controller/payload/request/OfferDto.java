package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record OfferDto(
        @NotBlank(message = "position-is-mandatory") String position,
        @NotNull(message = "salary-is-mandatory") double salary,
        @NotBlank(message = "startDate-is-mandatory") String startDate,
        @NotBlank(message = "endDate-is-mandatory") String endDate
        ) {
}
