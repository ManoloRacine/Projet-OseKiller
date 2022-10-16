package com.osekiller.projet.controller.payload.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record GeneralOfferDto(@NotNull Long offerId,
                              @NotNull Long companyId,
                              @NotBlank String companyName,
                              @NotBlank String position,
                              @NotNull Double salary,
                              @NotBlank String startDate,
                              @NotBlank String endDate) {

}
