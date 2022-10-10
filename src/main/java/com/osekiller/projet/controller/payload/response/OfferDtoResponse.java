package com.osekiller.projet.controller.payload.response;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record OfferDtoResponse(
        @NotBlank Long offerId,
        @NotBlank String position,
        @NotNull double salary,
        @NotBlank String startDate,
        @NotBlank String endDate,
        @NotBlank Resource offer
        ) {
}
