package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Offer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record GeneralOfferDto(@NotNull Long offerId,
                              @NotNull Long companyId,
                              @NotBlank String companyName,
                              @NotBlank String position,
                              @NotNull Double salary,
                              @NotBlank String startDate,
                              @NotBlank String endDate) {

    public static GeneralOfferDto from(Offer offer){
        return new GeneralOfferDto(
                offer.getId(), offer.getOwner().getId(), offer.getOwner().getName(), offer.getPosition(),
                offer.getSalary(), offer.getStartDate().toString(), offer.getEndDate().toString()
        );
    }

}
