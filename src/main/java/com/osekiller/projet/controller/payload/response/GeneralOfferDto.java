package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record GeneralOfferDto(@NotBlank Long offerId,
                              @NotBlank Long companyId,
                              @NotBlank String companyName,
                              @NotBlank String position,
                              @NotNull double salary,
                              @NotBlank String startDate,
                              @NotBlank String endDate) {

}
